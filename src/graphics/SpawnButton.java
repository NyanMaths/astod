package graphics;

import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.towers.Tower;



public class SpawnButton implements Drawable
{
private final Tower tower;
private final Point2D.Float position;
private static float halfWidth = 72.0f;
private static float halfHeight = 60.5f;

public SpawnButton (String towerName, Point2D.Float position)
{
	this.tower = Tower.fromName(towerName, new Point2D.Float(position.x - halfWidth + 17, position.y));
	this.position = position;
}

public String getTowerName ()
{
	return this.tower.getName();
}

public Tower spawn ()
{
	return Tower.fromName(this.tower.getName(), new Point2D.Float((float)StdDraw.mouseX(), (float)StdDraw.mouseY()));
}

public static float getHalfWidth ()
{
	return halfWidth;
}
public static boolean setHalfWidth (float newHalfWidth)
{
	if (newHalfWidth <= 0.0)
	{
		return false;
	}

	halfWidth = newHalfWidth;
	return true;
}
public static float getHalfHeight ()
{
	return halfHeight;
}
public static boolean setHalfHeight (float newHalfHeight)
{
	if (newHalfHeight <= 0.0)
	{
		return false;
	}

	halfHeight = newHalfHeight;
	return true;
}

public boolean isPressed ()
{
	double mouseX = StdDraw.mouseX();
	double mouseY = StdDraw.mouseY();

	return mouseX >= position.x - halfWidth && mouseX <= position.x + halfWidth && mouseY >= position.y - halfHeight && mouseY <= position.y + halfHeight;
}

@Override
public void draw ()
{	
	this.tower.draw();

	StdDraw.setPenColor(Color.BLACK);
	StdDraw.rectangle(this.position.x, this.position.y, halfWidth, halfHeight);  // draw button border

	// write stats
	StdDraw.text(this.tower.getPosition().x+60, this.tower.getPosition().y+50, this.tower.getName());  
	StdDraw.text(this.tower.getPosition().x+15, this.tower.getPosition().y+30,"HP : " + this.tower.getMaxHealth()); 
	StdDraw.text(this.tower.getPosition().x+80, this.tower.getPosition().y+30,"ATK : " + this.tower.getAttack());
	StdDraw.text(this.tower.getPosition().x+60, this.tower.getPosition().y+10,"Delay: " + this.tower.getAttackDelay());
	StdDraw.text(this.tower.getPosition().x+60, this.tower.getPosition().y-10,"Range : " + this.tower.getRange());
	StdDraw.text(this.tower.getPosition().x+50, this.tower.getPosition().y-30,"Element : " + this.tower.getElement());
	StdDraw.text(this.tower.getPosition().x+15, this.tower.getPosition().y-50,"$ : " + this.tower.getCost()); 
}
}