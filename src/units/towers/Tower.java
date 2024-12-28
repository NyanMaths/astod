package units.towers;

import java.awt.geom.Point2D;
import libraries.StdDraw;
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
public void draw ()
{
	StdDraw.setPenColor(this.getColour());
	StdDraw.filledRectangle(this.getPosition().x, this.getPosition().y, 10, 20);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.rectangle(this.getPosition().x, this.getPosition().y, 10.5, 20.5);
}
public void drawText()
{
	StdDraw.text(this.getPosition().x+60, this.getPosition().y+50, getName());  
	StdDraw.text(this.getPosition().x+15, this.getPosition().y+30,"HP : " + getMaxHealth()); 
	StdDraw.text(this.getPosition().x+80, this.getPosition().y+30,"ATK : " + getAttack());
	StdDraw.text(this.getPosition().x+60, this.getPosition().y+10,"Delay: " + getAttackDelay());
	StdDraw.text(this.getPosition().x+60, this.getPosition().y-10,"Range : " + getRange());
	StdDraw.text(this.getPosition().x+50, this.getPosition().y-30,"Element : " + getElement());
	StdDraw.text(this.getPosition().x+15, this.getPosition().y-50,"$ : " + getCost()); 
} 
}
