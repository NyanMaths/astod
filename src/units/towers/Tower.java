package units.towers;

import java.awt.geom.Point2D;
import libraries.StdDraw;
import map.Cell;
import units.Element;
import units.Unit;


/**
 * Class for the towers, inheriting Unit.
 * A tower canot move nor does it drop anything upon destuction, but it costs money to build.
 */
public abstract class Tower extends Unit
{
/**
 * Constructor for towers
 * @param name the tower type : Archer, Ice Caster...
 * @param attacker defaulted to true as towers are on player's side
 * @param element the tower's element
 * @param maxHealth the tower's max health points
 * @param attack the tower's attack damage
 * @param range the tower's range in tiles
 * @param attackDelay the tower's attack delay in milliseconds
 * @param spawnPosition the tower's spawn location
 */
public Tower (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition)
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
}

/**
 * Easier to use constructor for towers, defaults stats
 * @param name the tower's name
 * @param spawnPosition the spawn position, can be elsewhere than the map, like the shop :)
 * @return a new tower
*/
public static Tower fromName (String name, Point2D.Float spawnPosition)
{
	if(name == null) return null;
	return switch (name)
	{
		case "Archer" -> new Archer(spawnPosition);
		case "Earth Caster" -> new EarthCaster(spawnPosition);
		case "Fire Caster" -> new FireCaster(spawnPosition);
		case "Water Caster" -> new WaterCaster(spawnPosition);
		case "Wind Caster" -> new WindCaster(spawnPosition);
		case "Ice Caster" -> new IceCaster(spawnPosition);
		default -> null;
	};
}

/**
 * How much this will drain my wallet
 * @return the cost of the tower
 */
public abstract long getCost ();

/**
 * A tower does not move, so this is empty
 */
@Override
public void move ()
{

}

/**
 * Hurts the tower and frees its occupied tile if destructed
 * @param damage the attacker's attack points
 * @param damageElement the element of the attacker
 * @return the effectively inflicted damage
 */
@Override
public long hurt (long damage, Element damageElement)
{
	long damageTaken = super.hurt(damage, damageElement);

	if (this.getHealth()==0)
	{
		Cell here = map.getCell(map.getCellCoordinates(this.getPosition()));
		here.toggleOccupied();
	}
	return damageTaken;
}

/**
 * Draws the tower and its health bar
 */
@Override
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledRectangle(this.getPosition().x, this.getPosition().y, 14, 27);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.rectangle(this.getPosition().x, this.getPosition().y, 14.5, 27.5);
	this.drawHealthBar();
}
}
