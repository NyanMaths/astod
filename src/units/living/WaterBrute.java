package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * Brutalizes your base using water.
 */
public final class WaterBrute extends LivingEntity
{
public WaterBrute (Point2D.Float spawnPosition) 
{
	super("Water Brute", true, Element.Water, 30, 5, 3, 1000, spawnPosition, 2);
}

@Override
public long getReward ()
{
    return 3;
}
}
