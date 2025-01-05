package game;

import graphics.Coloured;
import graphics.Drawable;
import graphics.Utils;
import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.Element;
import units.living.LivingEntity;
import units.towers.Tower;


/**
 * Class used to represent the player and the ally base, used to buy towers and physically placed at the center of the base as the enemies' target
 */
public class Player implements Drawable, Coloured
{
/**
 * The player's name
 */
private final String name;
/**
 * The player's max health
 */
private final long maxHealth;
/**
 * The player's current health
 */
private long health;
/**
 * The player's negociation arguments
 */
private long money;
/**
 * The player's element, why not ? for now neutral and multipliers not implemented
 */
private final Element element;
/**
 * The player's position
 */
private Point2D.Float position;

/**
 * Constructor for player, for now used with default values but could be coonfigured later
 * @param name the player's name
 * @param element the player's element
 * @param maxHealth the player's max health
 * @param initialMoney the player's initial money
 * @param health the player's initial health points
 */
public Player (String name, Element element, long maxHealth, long initialMoney, long health)
{
	this.name = name;
	this.maxHealth = maxHealth;
	this.health = health;
	this.money = initialMoney;
	this.element = element;
}


/**
 * Life status of the player
 * @return the player's binary good state
 */
public boolean isAlive ()
{
	return this.health > 0;
}


/**
 * Setter for player's position
 * @param newPosition the new player's position
 */
public void setPosition (Point2D.Float newPosition)
{
	this.position = newPosition;
}

/**
 * Getter for player's name
 * @return the player's name
 */
public String getName ()
{
	return this.name;
}

/**
 * Getter for player's health
 * @return the player's health
 */
public long getHealth ()
{
	return this.health;
}
/**
 * Setter for player's health,
 * @param newHealth the new player's health points for
 * @return whether the new health were accepted or not
 */
public boolean setHealth (long newHealth)
{
	if (newHealth < 0)
	{
		return false;
	}

	this.health = newHealth;
	return true;
}

/**
 * Getter for player's max health
 * @return the player's max health
 */
public long getMaxHealth ()
{
	return this.maxHealth;
}
// setter useless in my opinion, debuffs on player's max health are cursed and I don't want to implement such a war crime

/**
 * Getter for player's money
 * @return something isomorphic to player's wallet weight
 */
public long getMoney ()
{
	return this.money;
}
/**
 * Takes the yoinked enemy and steals their loot
 * @param enemy the blighted enemy
 * @return the enmy's reward
 */
public long reward (LivingEntity enemy)
{
	this.money += enemy.getReward();
	return enemy.getReward();
}
/**
 * Allows the player to buy a tower.
 * @param towerName the kind of tower the player wants
 * @param spawnPosition where to spawn the tower
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


/**
 * Hurt the player
 * @param damage something isomorphic to some force's norm
*/
public void hurt (long damage)
{
	if (damage > this.health)
	{
		this.health = 0;
		return;
	}

	this.health -= damage;
}


/**
 * Getter for player's colour
 */
@Override
public Color getColour ()
{
	return Utils.colorFromElement(this.element);
}

/**
 * Draw player as a heart with their name above
 */
@Override
public void draw ()
{
	graphics.Utils.drawHeart(this.position.x, this.position.y, 25, StdDraw.BLACK);
	graphics.Utils.drawHeart(this.position.x, this.position.y, 23);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.text(this.position.x, this.position.y+25, this.name);
}
}
