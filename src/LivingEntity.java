/*
Class for the living entities, inheriting Unit.
A living entity can move and drops a reward when killed.
*/

public abstract class LivingEntity extends Unit
{
private long speed;
private static long reward;

public LivingEntity(String name, boolean attacker, Element element, long maxHealth, long attack, long range, long speed) 
{
	super(name, attacker, element, maxHealth, attack, range);
	this.speed = speed;
}


}
