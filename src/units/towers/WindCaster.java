package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/**
 * Makes a mess of its enemies hair, does not seem to do much more for now
 */
public final class WindCaster extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public WindCaster (Point2D.Float spawnPosition)
{
	super("Wind Caster", false, Element.Wind, 45, 3, 4.0f, 1000, spawnPosition);
}

/**
 * How much does it cost ?
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 50;
}
}
