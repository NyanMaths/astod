package units.towers;

import java.awt.geom.Point2D;
import units.Element;
import units.Unit;

/*
Class for the towers, inheriting Unit.
A tower canot move nor does it drop anything upon destuction, but it costs money to build.
*/
public class Tower extends Unit
{
private static final long COST = 12;

public Tower (String name, boolean attacker, Element element, long maxHealth, long attack, long range, long attackDelay, Point2D.Float spawnPosition) 
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
}

public static long getCost ()
{
	return COST;
}
}