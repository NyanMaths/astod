package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * Brutalizes your base.
 */
public final class EarthBrute extends LivingEntity
{
public EarthBrute (Point2D.Float spawnPosition)
{
	super("Earth Brute", true, Element.Earth, 30, 5, 3, 1000, spawnPosition, 2);
}

@Override
public float getAttackRadius ()
{
	return 1.5f;
}

@Override
public long getReward ()
{
    return 3;
}
}
