package units.towers;

import java.awt.geom.Point2D;
import units.Element;

/*
 * dunno
 */
public final class FireCaster extends Tower
{
public FireCaster (Point2D.Float spawnPosition)
{
	super("Fire Caster", false, Element.Fire, 30, 10, 2.5f, 500, spawnPosition);
}

@Override
public float getAttackRadius ()
{
	return 0.75f;
}

@Override
public long getCost ()
{
	return 100;
}
}
