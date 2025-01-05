package units.living;

import java.awt.geom.Point2D;
import units.Element;

/**
 * I don't even know what this thing can be, but it's fast.
 */
public final class FireGrognard extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public FireGrognard (Point2D.Float spawnPosition)
{
	super("Fire Grognard", true, Element.Fire, 1, 7, 3, 2000, spawnPosition, 4);
}

/**
 * Reward for player when killed
 */
@Override
public long getReward ()
{
    return 1;
}
}
