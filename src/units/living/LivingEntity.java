package units.living;

import java.awt.geom.Point2D;
import libraries.StdDraw;
import map.Cell;
import units.Element;
import units.Unit;

/*
Class for the living entities, inheriting Unit.
A living entity can move and drops a reward when killed.
*/

public abstract class LivingEntity extends Unit
{
protected long speed;
private Cell destination;

public LivingEntity (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition, long speed)
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
	this.speed = speed;
	this.destination =  map.getSpawn();
}

public abstract long getReward ();

public long getSpeed ()
{
	return this.speed;
}
public boolean setSpeed (long newSpeed)
{
	if (newSpeed < 0)
	{
		return false;
	}

	this.speed = newSpeed;
	return true;
}


/**
 * Make the unit progress towards the center of a cell.
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
		step.x /= stepLength * 3.0f / (float)this.speed;
		step.y /= stepLength * 3.0f / (float)this.speed;
		this.stride(step);
	}
}

@Override
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledCircle(this.getPosition().x, this.getPosition().y, 12);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.circle(this.getPosition().x, this.getPosition().y, 12.5);
	this.drawHealthBar(this.getPosition());
}
}
