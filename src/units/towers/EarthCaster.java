package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/*
 * dunno
 */
public final class EarthCaster extends Tower
{
public EarthCaster (Point2D.Float spawnPosition) 
{
	super("Earth Caster", false, Element.Earth, 50, 7, 2.5f, 500, spawnPosition);
}

@Override
public long getCost ()
{
	return 100;
}
}
