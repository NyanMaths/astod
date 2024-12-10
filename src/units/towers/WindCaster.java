package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/*
 * dunno
 */
public final class WindCaster extends Tower
{
public WindCaster (Point2D.Float spawnPosition) 
{
	super("Wind Caster", false, Element.Wind, 30, 3, 4.0f, 1000, spawnPosition);
}

@Override
public long getCost ()
{
	return 50;
}
}
