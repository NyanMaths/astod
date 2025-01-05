package units.towers;

import java.awt.geom.Point2D;
import units.AttackMode;
import units.Element;


/**
 * Weak but long-sighted
 */
public final class WaterCaster extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public WaterCaster (Point2D.Float spawnPosition)
{
	super("Water Caster", false, Element.Water, 30, 3, 4.0f, 1000, spawnPosition);
}

/**
 * Tactical Camel attack mode, not so fast...
 * @return the tower's attack mode
 */
@Override
public AttackMode getAttackMode ()
{
	return AttackMode.MostAdvanced;
}

/**
 * How much does it cost ?
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 50;
}
}
