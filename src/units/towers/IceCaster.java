package units.towers;

import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;
import java.util.List;
import map.Cell;
import units.AttackMode;
import units.Element;
import units.Unit;
import units.living.LivingEntity;

/*
 * dunno
 */
public final class IceCaster extends Tower
{
public IceCaster (Point2D.Float spawnPosition)
{
	super("Ice Caster", false, Element.Water, 40, 1, 5.0f, 2000, spawnPosition);
}

@Override
public AttackMode getAttackMode ()
{
	return AttackMode.Tankiest;
}

@Override
public float getAttackRadius ()
{
	return 1.0f;
}

@Override
public long getCost ()
{
	return 70;
}

@Override
protected void attack ()
{
	try
	{
	if (!this.isCooldowned())
	{
		if (this.getAttackRadius()*Cell.getSize() > Cell.getSize()/100.0)  // if the unit does aoe attacks
		{
			List<Unit> victims = level.getNearbyAllies(this.target, this.getAttackRadius()*Cell.getSize());
			victims.stream().filter(victim->victim != null).forEach(victim->victim.hurt(this.getAttack(), this.getElement()));
			victims.stream().filter(victim->victim != null).forEach(victim->((LivingEntity)victim).setSpeed((long)((double)((LivingEntity)victim).getSpeed()*0.70)));
		}
		else
		{
			this.target.hurt(this.getAttack(), this.getElement());
		}

		this.cooldown();
	}
	}
	catch (ConcurrentModificationException eee)
	{

	}
}
}
