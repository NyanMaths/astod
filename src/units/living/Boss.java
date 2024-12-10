package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
 * *Menacing approach*
 * This thing is gonna wreck your base, be prepared.
 */
public final class Boss extends LivingEntity
{
public Boss (Point2D.Float spawnPosition) 
{
	super("Boss", true, Element.Fire, 150, 100, 2, 10000, spawnPosition, 1);
}

@Override
public long getReward ()
{
    return 150;
}
}
