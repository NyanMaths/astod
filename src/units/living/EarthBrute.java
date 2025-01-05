package units.living;

import java.awt.geom.Point2D;
import units.Element;


/**
 * Brutalizes your base with dirt.
 * Does AoE damage, perfectly balanced, as all things should be.
 */
public final class EarthBrute extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public EarthBrute (Point2D.Float spawnPosition)
{
	super("Earth Brute", true, Element.Earth, 30, 5, 3, 1000, spawnPosition, 2);
}

/**
 * Attack AoE field radius
 */
@Override
public float getAttackRadius ()
{
	return 1.5f;
}

/**
 * Reward for player when killed
 */
@Override
public long getReward ()
{
    return 3;
}
}
