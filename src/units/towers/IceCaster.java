package units.towers;

import java.awt.geom.Point2D;
import java.util.ConcurrentModificationException;
import java.util.List;
import map.Cell;
import units.AttackMode;
import units.Element;
import units.Unit;
import units.living.LivingEntity;

/**
 * Hits light, but so friendly your enemies will never leave it behind
 */
public final class IceCaster extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public IceCaster (Point2D.Float spawnPosition)
{
	super("Ice Caster", false, Element.Water, 40, 1, 5.0f, 2000, spawnPosition);
}

/**
 * Tactical Camel attack mode, YE SHALL NOT PASS
 * @return the tower's attack mode
 */
@Override
public AttackMode getAttackMode ()
{
	return AttackMode.MostAdvanced;
}

/**
 * AoE damage field radius
 * @return the tower's attack radius
 */
@Override
public float getAttackRadius ()
{
	return 1.0f;
}

/**
 * How costly this tower is
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 70;
}

/**
 * Attacks and slows enemies by 30% of their current speed, maybe nerf this later
 */
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
			victims.stream().filter(victim->victim != null).forEach(victim->((LivingEntity)victim).setSpeed((((LivingEntity)victim).getSpeed()*0.70f)));
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
