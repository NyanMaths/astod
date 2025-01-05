package units.living;

import java.awt.geom.Point2D;
import units.Element;

/**
 * The most basic enemy : the minion
 * Weak yet reliable, this is the best companion of a fearsome villain.
 */
public final class Minion extends LivingEntity
{
/**
 * Constructor of the unit, uses default values for now
 * @param spawnPosition where to spawn the new unit
 */
public Minion (Point2D.Float spawnPosition)
{
	super("Minion", true, Element.Neutral, 10, 3, 0, 0, spawnPosition, 2);
}

/**
 * Reward for player when (free) killed
 */
@Override
public long getReward ()
{
	return 1;
}
}
