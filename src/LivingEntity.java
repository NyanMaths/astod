
import java.awt.geom.Point2D;

/*
Class for the living entities, inheriting Unit.
A living entity can move and drops a reward when killed.
*/

public abstract class LivingEntity extends Unit
{
private long speed;
private static long reward;

public LivingEntity(String name, boolean attacker, Element element, long maxHealth, long attack, long range, long attackDelay, Point2D.Float spawnPosition, long speed) 
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
	this.speed = speed;
}


}
