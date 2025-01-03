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

@Override
public void move ()
{
	if (this.destination == null)
	{
		level.slapPlayer(this);
	}
	else if (this.getPosition().distance(this.destination.getCenter()) < Cell.getSize()/10.0f)
	{
		this.destination = destination.getNextCell();
	}
	else
	{
		Point2D.Float nextPosition = destination.getCenter();
		Float nextX = 1.0f/3.0f * (float)this.speed * (nextPosition.x-this.getPosition().x);
		Float nextY = 1.0f/3.0f * (float)this.speed * (nextPosition.y-this.getPosition().y);
		Point2D.Float difference = new Point2D.Float(nextPosition.x-this.getPosition().x,nextPosition.y-this.getPosition().y);
		Double norme = Math.sqrt(difference.x*difference.x + difference.y*difference.y);
		this.getPosition().x += nextX/norme;
		this.getPosition().y += nextY/norme;
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
