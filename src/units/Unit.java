package units;

import game.Level;
import graphics.Coloured;
import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Timer;
import libraries.StdDraw;
import map.Cell;
import map.Map;


/*
Unit : an abstract class to define basics of towers and living entities.
The default attack mode is intended to be defined by the player directly in order not to bother setting it manually each time they invoke an ally.
The attacker property is a flag to identify a unit as ally or enemy.
*/
public abstract class Unit implements Drawable, Coloured
{
private static AttackMode defaultAttackMode = AttackMode.Nearest;

private String name;
private Element element;
private boolean attacker;

private long maxHealth;
private long health;
private long attack;
private float range;  // unit : tiles
private long attackDelay;  // unit : milliseconds
private AttackMode attackMode;
private Unit target;
private boolean isInCooldown;

private final static Timer rechargeScheduler = new Timer();

// stored as proportion of the screen (centered => (0.5, 0.5) for instance)
// This is intended to be able to resize the game window without making it die.
// lol nope absolute positioning goes brrrr
private Point2D.Float position;

// the level the units belong to, used to get nearby units to attack or heal
protected static Level level = null;
protected static Map map = null;

public Unit (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition, long health)
{
    this.name = name;
    this.attacker = attacker;
    this.element = element;

    this.maxHealth = maxHealth;
    this.health = health;
    this.attack = attack;
    this.range = range;
	this.attackDelay = attackDelay;
    this.attackMode = defaultAttackMode;
	this.target = null;
	this.isInCooldown = false;

	this.position = spawnPosition;
}
/* Initializes a new Unit with full health, use this if unsure, the other constructor is intended to implement debuffs.
 */
public Unit (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition)
{
    this(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition, maxHealth);
}


/*
 * Sets the level to ask for nearby units when attacking, healing and so on
 */
public static boolean setLevel (Level newLevel)
{
	if (newLevel == null)
	{
		return false;
	}

	level = newLevel;
	return true;
}

public static boolean setMap (Map newMap)
{
	if (newMap == null)
	{
		return false;
	}

	map = newMap;
	return true;
}

/**
 *
 * @param what kind of element is applied to the Unit
 * @return the multiplier to be applied to the damage taken
 */
private float getDamageMultiplier (Element damageElement)
{
	if ((this.element == Element.Fire && damageElement == Element.Water) ||
		(this.element == Element.Water && damageElement == Element.Wind) ||
		(this.element == Element.Wind && damageElement == Element.Earth) ||
		(this.element == Element.Earth && damageElement == Element.Fire))
	{
		return 1.5f;
	}

	return 1.0f;
}


public String getName ()
{
	return this.name;
}

public Element getElement ()
{
	return this.element;
}

public boolean isAttacker ()
{
	return this.attacker;
}
public void setAttacker (boolean newStatus)  // May require special magician token to allow operation as it's quite uncommon to switch teams as easily as Ciotti
{
	this.attacker = newStatus;
}

public boolean isAlive ()
{
	return this.health > 0;
}

public long getMaxHealth ()
{
	return this.maxHealth;
}
/** Setter for Unit's max health
 * @return whether the new value is acceptable (> 1) or not
 */
public boolean setMaxHealth (long newMaxHealth)  // May also require special magician token to allow operation
{
	if (newMaxHealth < 1)
	{
		return false;
	}

	this.maxHealth = newMaxHealth;
	if (this.health > newMaxHealth)
	{
		this.health = newMaxHealth;
	}
	return true;
}

public long getHealth ()
{
	return this.health;
}

/** Hurts or heals the Unit
 * @return the damage actually taken by the Unit (negative if healed)
*/
public long hurt (long damage, Element damageElement)
{
	long damageToInflict = damage;

	if (damage > 0)
	{
		damageToInflict = (long)((float)damage * getDamageMultiplier(damageElement));
	}


	if (damageToInflict < this.health && damageToInflict >= 0)  // Normal damage case
	{
		this.health -= damageToInflict;
		return damageToInflict;
	}
	else if (damageToInflict < 0)  // Healing case
	{
		long uncappedHealth = this.health + damageToInflict;

		if (uncappedHealth > this.maxHealth)
		{
			this.health = this.maxHealth;
			return damageToInflict + uncappedHealth - this.maxHealth;
		}

		this.health = uncappedHealth;
		return damageToInflict;
	}


	// Dies from death

	long currentHealth = this.health;
	this.health = 0;
	level.blight(this);
	return currentHealth;
}

public long getAttack ()
{
	return this.attack;
}
// Setter too dangerous for now

public float getRange ()
{
	return this.range;
}
// Setter useless for now

public long getAttackDelay ()
{
	return this.attackDelay;
}
public boolean setAttackDelay (long newAttackDelay)
{
	if (newAttackDelay < 0)
	{
		return false;
	}

	this.attackDelay = newAttackDelay;
	return true;
}

public AttackMode getAttackMode ()
{
	return this.attackMode;
}
public void setAttackMode (AttackMode newAttackmode)
{
	this.attackMode = newAttackmode;
}

public float getAttackRadius ()
{
	return 0.0f;
}

public Point2D.Float getPosition ()
{
	return this.position;
}
public void setPosition (Point2D.Float newPosition)
{
	this.position = newPosition;
}

public double distance (Unit other)
{
	return this.getPosition().distance(other.getPosition());
}


private void cooldown ()
{
	this.isInCooldown = true;

	rechargeScheduler.schedule(new RechargeTask(this), this.attackDelay);

}
public void recharge ()
{
	this.isInCooldown = false;
}

private void hurtAux (Unit unit)
{
	if (unit != null)
	{
		unit.hurt(this.attack, this.element);
	}
}
private void attack ()
{
	try
	{
	if (!this.isInCooldown)
	{
		System.out.println(this.getAttackRadius());

		if (this.getAttackRadius()*Cell.getSize() > Cell.getSize()/100.0)  // if the unit does aoe attacks
		{
			List<Unit> victims = level.getNearbyAllies(this.target, this.getAttackRadius()*Cell.getSize());
			victims.stream().forEach(victim->this.hurtAux(victim));
		}
		else
		{
			this.target.hurt(this.attack, this.element);
		}

		this.cooldown();
	}
	}
	catch (ConcurrentModificationException eee)
	{

	}
}

public void tick ()
{
	if (this.target == null || !this.target.isAlive())
	{
		this.target = level.getNearest(this, this.range, this.attackMode);
	}
	if (this.target != null && this.target.distance(this) > this.range*Cell.getSize())
	{
		this.target = null;
	}

	if (this.target != null)
	{
		this.attack();
	}
	else
	{
		this.move();
	}
}

public abstract void move ();


public void drawHealthBar (Point2D.Float position)
{
	/*position = this.getPosition();
	double healthRemaning = this.getHealth()/this.getMaxHealth();
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.filledRectangle(position.x,position.y+20,10,5);
	StdDraw.setPenColor(Color.GREEN);
	StdDraw.filledRectangle(position.x,position.y+20,9*healthRemaning,4);
	if (healthRemaning < 1)
	{
	StdDraw.setPenColor(Color.RED);
	StdDraw.rectangle(position.x,position.y+20,9*(1-healthRemaning),4);
	}
	StdDraw.setPenColor(Color.BLACK);*/
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.rectangle(this.position.x, this.position.y+20, 10.5, 3.5);
	StdDraw.setPenColor(StdDraw.GREEN);
	StdDraw.filledRectangle(this.position.x, this.position.y+20, (float)this.health/(float)this.maxHealth * 10.5f, 3.0);
}

@Override
public Color getColour ()
{
	return graphics.Utils.colorFromElement(this.element);
}
}
