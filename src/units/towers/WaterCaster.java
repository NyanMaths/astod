package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/*
 * dunno
 */
public final class WaterCaster extends Tower
{
public WaterCaster (Point2D.Float spawnPosition)
{
	super("Water Caster", false, Element.Water, 30, 3, 4.0f, 1000, spawnPosition);
}

@Override
public long getCost ()
{
	return 50;
}
}
