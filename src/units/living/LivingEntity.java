package units.living;

import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.Element;
import units.Unit;

/*
Class for the living entities, inheriting Unit.
A living entity can move and drops a reward when killed.
*/

public abstract class LivingEntity extends Unit
{
protected long speed;

public LivingEntity (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition, long speed) 
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
	this.speed = speed;
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
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledCircle(this.getPosition().x, this.getPosition().y, 20);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.circle(this.getPosition().x, this.getPosition().y, 20.5);
}
}
