package units.towers;

import java.awt.geom.Point2D;
import units.AttackMode;
import units.Element;

/**
 * Throws devastating dirt to the tankiest enemies.
 */
public final class EarthCaster extends Tower
{
/**
 * Constructor of the tower, uses default values for now
 * @param spawnPosition where to spawn the new tower
 */
public EarthCaster (Point2D.Float spawnPosition)
{
	super("Earth Caster", false, Element.Earth, 50, 7, 2.5f, 500, spawnPosition);
}

/**
 * No-Brain attack mode
 * @return the towoer's attack mode
 */
@Override
public AttackMode getAttackMode ()
{
	return AttackMode.Tankiest;
}

/**
 * AoE damage field radius
 * @return the tower's attack radius
 */
@Override
public float getAttackRadius ()
{
	return 1.0f;
}

/**
 * How much tihs will cost
 * @return the tower's cost
 */
@Override
public long getCost ()
{
	return 100;
}
}
