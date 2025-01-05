package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/**
 * Fire burns, your enemies will soon feel it
 */
public final class FireCaster extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public FireCaster (Point2D.Float spawnPosition)
{
	super("Fire Caster", false, Element.Fire, 30, 10, 2.5f, 500, spawnPosition);
}

/**
 * Aoe damage field radius
 * @return the tower's attack radius
 */
@Override
public float getAttackRadius ()
{
	return 0.75f;
}

/**
 * How much this tower will cost you
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 100;
}
}
