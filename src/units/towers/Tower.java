package units.towers;

import java.awt.geom.Point2D;
import libraries.StdDraw;
import map.Cell;
import units.Element;
import units.Unit;

/*
Class for the towers, inheriting Unit.
A tower canot move nor does it drop anything upon destuction, but it costs money to build.
*/
public abstract class Tower extends Unit
{
public Tower (String name, boolean attacker, Element element, long maxHealth, long attack, float range, long attackDelay, Point2D.Float spawnPosition)
{
	super(name, attacker, element, maxHealth, attack, range, attackDelay, spawnPosition);
}

/*
 * Constructor from name
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
		default -> null;
	};
}

public abstract long getCost ();

@Override
public void move ()
{

}

@Override
public long hurt(long damage, Element damageElement)
{
	long damageTaken = super.hurt(damage, damageElement);
	if (this.getHealth()==0)
	{
		Cell here = map.getCell(map.getCellCoordinates(this.getPosition()));
		here.toggleOccupied();
	}
	return damageTaken;
}
@Override
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledRectangle(this.getPosition().x, this.getPosition().y, 14, 27);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.rectangle(this.getPosition().x, this.getPosition().y, 14.5, 27.5);
	this.drawHealthBar(this.getPosition());
}
}
