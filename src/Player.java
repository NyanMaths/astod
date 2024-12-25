import graphics.Coloured;
import graphics.Drawable;
import graphics.Utils;
import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.Element;
import units.living.LivingEntity;
import units.towers.Tower;


public class Player implements Drawable, Coloured
{
private final String name;
private final long maxHealth;
private long health;
private long money;
private final Element element;
private final Point2D.Float position;

public Player (String name, Element element, long maxHealth, long initialMoney, Point2D.Float spawnPosition, long health)
{
	this.name = name;
	this.maxHealth = maxHealth;
	this.health = health;
	this.money = initialMoney;
	this.element = element;
	this.position = spawnPosition;
}


public String getName ()
{
	return this.name;
}

public long getHealth ()
{
	return this.health;
}
public boolean isAlive ()
{
	return this.health > 0;
}
public boolean setHealth (long newHealth)
{
	if (newHealth < 0)
	{
		return false;
	}

	this.health = newHealth;
	return true;
}

public long getMaxHealth ()
{
	return this.maxHealth;
}
// setter useless in my opinion, debuffs on player's max health are cursed and I don't want to implement such a war crime


public long getMoney ()
{
	return this.money;
}
/*
 * Takes the yoinked enemy and steals their loot
 * @return the enmy's reward
 */
public long reward (LivingEntity enemy)
{
	this.money += enemy.getReward();
	return enemy.getReward();
}
/*
 * Allows the player to buy a tower.
 * @return the bought tower, null if you are too poor or can't type
 */
public Tower buy (String towerName, Point2D.Float spawnPosition)
{
	Tower newTower = Tower.fromName(towerName, spawnPosition);

	if (this.money >= newTower.getCost())
	{
		this.money -= newTower.getCost();
		return newTower;
	}

	return null;
}


@Override
public Color getColour ()
{
	return Utils.colorFromElement(this.element);
}

@Override
public void draw ()
{
	StdDraw.setPenColor(StdDraw.PINK);
	StdDraw.filledCircle(this.position.x, this.position.y, 20);
}
}
