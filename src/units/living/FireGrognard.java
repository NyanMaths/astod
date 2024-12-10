package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * I don't even know what this thing can be.
 */
public final class FireGrognard extends LivingEntity
{
public FireGrognard (Point2D.Float spawnPosition) 
{
	super("Fire Grognard", true, Element.Fire, 1, 7, 3, 2000, spawnPosition, 4);
}

@Override
public long getReward ()
{
    return 1;
}
}
