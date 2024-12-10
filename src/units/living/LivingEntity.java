package units.living;

import java.awt.geom.Point2D;
import units.Element;
import units.Unit;

/*
Class for the living entities, inheriting Unit.
A living entity can move and drops a reward when killed.
*/

public abstract class LivingEntity extends Unit
{
protected long speed;
protected static final long REWARD = 12;

public LivingEntity (String name, boolean attacker, Element element, long maxHealth, long attack, long range, long attackDelay, Point2D.Float spawnPosition, long speed) 
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
	this.speed = speed;
}

public static long getReward ()
{
	return REWARD;
}

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
}
