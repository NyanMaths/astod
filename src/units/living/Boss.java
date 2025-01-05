package units.living;

import java.awt.geom.Point2D;
import units.Element;


/**
 * *Menacing approach*
 * This thing is gonna wreck your base, be prepared.
 */
public final class Boss extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public Boss (Point2D.Float spawnPosition)
{
	super("Boss", true, Element.Fire, 150, 100, 2, 10000, spawnPosition, 1);
}

/**
 * Reward property for the player when killed
 */
@Override
public long getReward ()
{
    return 150;
}
}
