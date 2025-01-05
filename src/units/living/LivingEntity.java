package units.living;

import java.awt.geom.Point2D;
import libraries.StdDraw;
import map.Cell;
import units.Element;
import units.Unit;


/**
Class for the living entities (enemies)
A living entity can move and drops a reward when killed
*/
public abstract class LivingEntity extends Unit
{
/**
 * The speed multiplier of the entity
 */
protected float speed;
/**
 * The Cell the entity strides towards
 */
private Cell destination;

/**
 * Construcor of LivingEntity
 * @param name the name of the entity
 * @param attacker its status
 * @param element its element
 * @param maxHealth its max health points
 * @param attack its attack points
 * @param range its range in tiles
 * @param attackDelay its attack delay in milliseconds
 * @param spawnPosition its initial position
 * @param speed its speed multiplier
 */
public LivingEntity (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition, float speed)
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
	this.speed = speed;
	this.destination =  map.getSpawn();
}

/**
 * Getter for entitty's reward when killed
 * @return the entity's reward
 */
public abstract long getReward ();

/**
 * Getter for entity's speed
 * @return the entity's speed multiplier
 */
public float getSpeed ()
{
	return this.speed;
}
/**
 * Setter for entity's speed
 * @param newSpeed the new entity's speed multiplier
 * @return if newSpeed was a nice speed
 */
public boolean setSpeed (float newSpeed)
{
	if (newSpeed < 0.0)
	{
		return false;
	}

	this.speed = newSpeed;
	return true;
}


/**
 * Make the unit progress towards the center of their destination cell.
 * If it reaches the center, target the next cell's center and hurts the player if the base is reached
 */
@Override
public void move ()
{
	if (this.destination == null)
	{
		level.slapPlayer(this);
	}
	else if (this.getPosition().distance(this.destination.getCenter()) < Cell.getSize()/30.0f)
	{
		this.destination = destination.getNextCell();
	}
	else
	{
		Point2D.Float step = new Point2D.Float(destination.getCenter().x-this.getPosition().x, destination.getCenter().y-this.getPosition().y);
		float stepLength = (float)Math.sqrt(step.x*step.x + step.y*step.y);
		step.x /= stepLength * 3.0f / this.speed;
		step.y /= stepLength * 3.0f / this.speed;
		this.stride(step);
	}
}

/**
 * Draws the entity as a circle with its health bar
 */
@Override
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledCircle(this.getPosition().x, this.getPosition().y, 12);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.circle(this.getPosition().x, this.getPosition().y, 12.5);
	this.drawHealthBar();
}
}
