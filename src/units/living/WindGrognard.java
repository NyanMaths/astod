package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * I don't even know what this thing can be.
 */
public final class WindGrognard extends LivingEntity
{
public WindGrognard (Point2D.Float spawnPosition)
{
	super("Wind Grognard", true, Element.Wind, 1, 7, 5, 2000, spawnPosition, 4);
}

@Override
public long getReward ()
{
    return 1;
}
}
