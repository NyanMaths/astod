package units.towers;

import java.awt.geom.Point2D;
import units.AttackMode;
import units.Element;


/**
 * This is a basic tower, as reliable as ever
 * Tactical Camel buff : targets the nearest enemy from your base
 */
public final class Archer extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public Archer (Point2D.Float spawnPosition)
{
	super("Archer", false, Element.Neutral, 30, 5, 2.5f, 1000, spawnPosition);
}

/**
 * Tactical Camel Attack mode
 * @return the tower's attack mode
 */
@Override
public AttackMode getAttackMode ()
{
	return AttackMode.MostAdvanced;
}

/**
 * How much it will drain from your wallet
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 20;
}
}
