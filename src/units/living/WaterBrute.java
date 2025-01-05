package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * Brutalizes your base using water.
 */
public final class WaterBrute extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public WaterBrute (Point2D.Float spawnPosition)
{
	super("Water Brute", true, Element.Water, 30, 5, 3, 1000, spawnPosition, 2);
}

/**
 * The AoE field radius
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
