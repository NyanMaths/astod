package units.living;

import java.awt.geom.Point2D;
import units.Element;

/**
 * I don't even know what this thing can be, but it's a fast boi
 */
public final class WindGrognard extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public WindGrognard (Point2D.Float spawnPosition)
{
	super("Wind Grognard", true, Element.Wind, 1, 7, 5, 2000, spawnPosition, 4);
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
