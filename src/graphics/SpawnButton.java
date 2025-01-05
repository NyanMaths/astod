package graphics;

import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.towers.Tower;


/**
 * This class allows buying towers by providing a button to place in the shop
 */
public class SpawnButton implements Drawable
{
/**
 * The purchasable tower
 */
private final Tower tower;
/**
 * The button's physical position
 */
private final Point2D.Float position;
/**
 * The half-width of the button
 */
private static float halfWidth = 72.0f;
/**
 * The half-height of the button
 */
private static float halfHeight = 60.5f;


/**
 * Constructor for SpawnButton
 * @param towerName the kind of tower the button spawns
 * @param position the physical position of the button
 */
public SpawnButton (String towerName, Point2D.Float position)
{
	this.tower = Tower.fromName(towerName, new Point2D.Float(position.x - halfWidth + 19, position.y));
	this.position = position;
}

/**
 * Getter for button's purchasable tower name
 * @return the kind of tower held by the instance
 */
public String getTowerName ()
{
	return this.tower.getName();
}

/**
 * Spawn the helf tower at the mouse's position
 * @return the newly spawned tower
 */
public Tower spawn ()
{
	return Tower.fromName(this.tower.getName(), new Point2D.Float((float)StdDraw.mouseX(), (float)StdDraw.mouseY()));
}

/**
 * Getter for button's half-width
 * @return the button's half-width
 */
public static float getHalfWidth ()
{
	return halfWidth;
}
/**
 * Setter for button's half-width
 * @param newHalfWidth the new button's half-width
 * @return whether the new half-width was acceptable or not
 */
public static boolean setHalfWidth (float newHalfWidth)
{
	if (newHalfWidth <= 0.0)
	{
		return false;
	}

	halfWidth = newHalfWidth;
	return true;
}

/**
 * Getter for button's half-height
 * @return the button's half-height
 */
public static float getHalfHeight ()
{
	return halfHeight;
}
/**
 * Setter for button's half-height
 * @param newHalfHeight the new button's half-height
 * @return whether the new half-height was acceptable or not
 */
public static boolean setHalfHeight (float newHalfHeight)
{
	if (newHalfHeight <= 0.0)
	{
		return false;
	}

	halfHeight = newHalfHeight;
	return true;
}

/**
 * Know if the button is being pressed
 * @return whether the button is being pressed or not
 */
public boolean isPressed ()
{
	double mouseX = StdDraw.mouseX();
	double mouseY = StdDraw.mouseY();

	return mouseX >= position.x - halfWidth && mouseX <= position.x + halfWidth && mouseY >= position.y - halfHeight && mouseY <= position.y + halfHeight;
}

/**
 * Draws the button : a border, a colour and the tower's stats
 */
@Override
public void draw ()
{
	this.tower.draw();

	StdDraw.setPenColor(Color.BLACK);
	StdDraw.rectangle(this.position.x, this.position.y, halfWidth, halfHeight);  // draw button border

	// write stats

	float towerX = this.tower.getPosition().x;
	float towerY = this.tower.getPosition().y;
	StdDraw.text(towerX+60, towerY+50, this.tower.getName());
	StdDraw.text(towerX+60, towerY+30,"HP : " + this.tower.getMaxHealth());
	StdDraw.text(towerX+60, towerY+10,"ATK : " + this.tower.getAttack());
	StdDraw.text(towerX+60, towerY-10,"Delay: " + this.tower.getAttackDelay());
	StdDraw.text(towerX+60, towerY-30,"Range : " + this.tower.getRange());
	StdDraw.text(towerX+20, towerY-50,"$ : " + this.tower.getCost());
}
}
