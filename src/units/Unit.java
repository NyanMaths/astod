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


/**
 * Unit : an abstract class to define basics of towers and living entities.
 * The default attack mode is intended to be defined by the player directly in order not to bother setting it manually each time they invoke an ally.
 * The attacker property is a flag to identify a unit as ally or enemy.
 */
public abstract class Unit implements Drawable, Coloured
{
/**
 * The unit's type
 */
private String name;
/**
 * The unit's element
 */
private Element element;
/**
 * The historic side of the unit
 */
private boolean attacker;

/**
 * The unit's max health points
 */
private long maxHealth;
/**
 * The unit's health points
 */
private long health;
/**
 * The unit's attack points
 */
private long attack;
/**
 * The unit's range in tiles
 */
private float range;
/**
 * The unit's attack delay in milliseconds
 */
private long attackDelay;
/**
 * The current unit's target to hurt or heal
 */
protected Unit target;
/**
 * The cooldowned status of the unit
 */
private boolean isInCooldown;

/**
 * The attack cooldowns scheduler
 */
private final static Timer rechargeScheduler = new Timer();


/**
 * Stored as proportion of the screen (centered => (0.5, 0.5) for instance)
 * This is intended to be able to resize the game window without making it die.
 * lol nope absolute positioning goes brrrr
 */
private Point2D.Float position;
/**
 * How close the unit is from the players base
 */
private float advancement;

/**
 * The level the units belong to, used to get nearby units to attack or heal
 */
protected static Level level = null;
/**
 * The map to refer to
 */
protected static Map map = null;

/**
 * Constructor for Unit, unsafe as there is no stats validation, be careful with this
 * @param name the name of the unit
 * @param attacker the side of the unit
 * @param element the unit's element
 * @param maxHealth the unit's max health points
 * @param attack the unit's attack points
 * @param range the unit's range
 * @param attackDelay the unit's attack delay in milliseconds
 * @param spawnPosition the unit's initial position
 * @param health the unit's initial health points
 */
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
	this.target = null;
	this.isInCooldown = false;

	this.position = spawnPosition;
	this.advancement = 0.0f;
}
/**
 * Constructor of Unit, defaults health to maxHealth
 * @param name the name of the unit
 * @param attacker the side of the unit
 * @param element the unit's element
 * @param maxHealth the unit's max health points
 * @param attack the unit's attack points
 * @param range the unit's range
 * @param attackDelay the unit's attack delay in milliseconds
 * @param spawnPosition the unit's initial position
 */
public Unit (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition)
{
    this(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition, maxHealth);
}


/**
 * Sets the level to ask for nearby units when attacking, healing and so on
 * @param newLevel the level to request information to
 * @return whether the level was based or not
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

/**
 * Setter for Unit's map to get informations from
 * @param newMap the new map to use
 * @return if the map was not null
 */
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
 * Get the damage multiplier between the instance element and the other
 * @param damageElement what kind of element is applied to the Unit
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


/**
 * Which side of history are you on ?
 * @return if the unit is an attacker or attacked
 */
public boolean isAttacker ()
{
	return this.attacker;
}
/**
 * Funny way to mess with the enmy
 * @param newStatus the new side of the unit
 */
public void setAttacker (boolean newStatus)  // May require special magician token to allow operation as it's quite uncommon to switch teams as easily as politicians
{
	this.attacker = newStatus;
}

/**
 * Life-status of the unit
 * @return whether the unit is alive or not
 */
public boolean isAlive ()
{
	return this.health > 0;
}


/**
 * Getter for unit's name
 * @return the unit's type
 */
public String getName ()
{
	return this.name;
}

/**
 * Getter for unit's element
 * @return the unit's element
 */
public Element getElement ()
{
	return this.element;
}

/**
 * Getter for unit's max health points
 * @return the unit's max health
 */
public long getMaxHealth ()
{
	return this.maxHealth;
}
/**
 * Setter for Unit's max health
 * @param newMaxHealth the new unit's max health points count
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

/**
 * Getter for unit's current health
 * @return the current unit's health
 */
public long getHealth ()
{
	return this.health;
}

/**
 * Offsets the unit's health, negatively(hurt) or positively (heal)
 * @param damage the gamage to inflict, negative for healing
 * @param damageElement the damage element to get the right multiplier, ignored for healing
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

/**
 * Getter for unit's attack
 * @return the unit's attack points
 */
public long getAttack ()
{
	return this.attack;
}
// Setter too dangerous for now

/**
 * Getter for unit's range
 * @return the unit's range in tiles
 */
public float getRange ()
{
	return this.range;
}
// Setter useless for now

/**
 * Getter for unit's attack delay
 * @return the unit's attack delay
 */
public long getAttackDelay ()
{
	return this.attackDelay;
}
/**
 * Funny setter for unit's attack delay
 * @param newAttackDelay the new attack delay in milliseconds
 * @return whether it was a good choice or not
 */
public boolean setAttackDelay (long newAttackDelay)
{
	if (newAttackDelay < 0)
	{
		return false;
	}

	this.attackDelay = newAttackDelay;
	return true;
}

/**
 * Getter for unit's attack mode
 * @return the unit's taget selection filter
 */
public AttackMode getAttackMode ()
{
	return AttackMode.Nearest;
}

/**
 * Getter for unit's attack radius
 * @return the unit's AoE damage field radius
 */
public float getAttackRadius ()
{
	return 0.0f;
}

/**
 * Getter for unit's position
 * @return the unit's position, BY REFERENCE, I'M SCARED NOW I NEED TO FIX THIS
 */
public Point2D.Float getPosition ()
{
	return this.position;
}
/**
 * Adds step to the position and updates the advancement towards the player's base
 * Never make a unit stride backwards as it would mess with the advancement for now
 * @param step the elementary move of the unit
*/
public void stride (Point2D.Float step)
{
	this.position.x += step.x;
	this.position.y += step.y;

	this.advancement += (float)Math.sqrt(step.x*step.x + step.y*step.y);
}

/**
 * Getter for unit's advancement on the path to victory
 * @return the strided distance towards the player's base
 */
public float getAdvancement ()
{
	return this.advancement;
}

/**
 * Computes euclidian distances between units
 * @param other the distant unit
 * @return let norm:v |-> sqrt(v.x² + v.y²) in norm(this.position-other.position)
 */
public double distance (Unit other)
{
	return this.getPosition().distance(other.getPosition());
}


/**
 * Cooldown status of the unit
 * @return whether the unit is cooldowned or not
 */
public boolean isCooldowned ()
{
	return this.isInCooldown;
}

/**
 * Makes the unit wait to fire again
 */
protected void cooldown ()
{
	this.isInCooldown = true;

	rechargeScheduler.schedule(new RechargeTask(this), this.attackDelay);

}
/**
 * Uncooldowns the unit
 */
public void recharge ()
{
	this.isInCooldown = false;
}

/**
 * Makes the unit hurt its target
 */
protected void attack ()
{
	try
	{
	if (!this.isInCooldown)
	{
		if (this.getAttackRadius()*Cell.getSize() > Cell.getSize()/100.0)  // if the unit does aoe attacks
		{
			List<Unit> victims = level.getNearbyAllies(this.target, this.getAttackRadius()*Cell.getSize());
			victims.stream().filter(victim->victim != null).forEach(victim->victim.hurt(this.attack, this.element));
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

/**
 * Attack or move if there is no valid target
 */
public void tick ()
{
	if (this.target == null || !this.target.isAlive())
	{
		this.target = level.getNearest(this, this.range*Cell.getSize(), this.getAttackMode());
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

/**
 * Moves the unit
 */
public abstract void move ();


/**
 * Draws the health bar of the unit above its position
 */
public void drawHealthBar ()
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

/**
 * Getter for unit's colour
 * @return the unit's colour
 */
@Override
public Color getColour ()
{
	return graphics.Utils.colorFromElement(this.element);
}
}
