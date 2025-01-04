package units.towers;

import java.awt.geom.Point2D;
import units.AttackMode;
import units.Element;


/*
 * This is a basic tower, reliable as ever.
 */
public final class Archer extends Tower
{
public Archer (Point2D.Float spawnPosition)
{
	super("Archer", false, Element.Neutral, 30, 5, 2.5f, 1000, spawnPosition);
}

@Override
public AttackMode getAttackMode ()
{
	return AttackMode.MostAdvanced;
}

@Override
public long getCost ()
{
	return 20;
}
}
