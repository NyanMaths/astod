package graphics;

import java.awt.Color;
import libraries.StdDraw;
import units.Element;


/**
 * Implementation for various graphics utilities
 */
public abstract class Utils
{
/**
 * Get a colour associated with an element
 * @param element the element to get the colour from
 * @return the associated colour
 */
public static java.awt.Color colorFromElement (Element element)
{
	return switch (element)
	{
		case Element.Neutral -> StdDraw.GRAY;
		case Element.Earth -> new Color(0, 167, 15);
		case Element.Wind -> new Color(242, 211, 0);
		case Element.Fire -> new Color(184, 22, 1);
		case Element.Water -> new Color(6, 0, 160);
	};
}

/**
 * Draws a heart wherever you want
 * @param x the x-coordinate of the center
 * @param y the y-coordinate of the center
 * @param height the height of the heart
 * @param colour the colour of the heart
 */
public static void drawHeart(double x, double y, double height, java.awt.Color colour)
{
	double centerX = x;
	double centerY = y;
	double halfHeight = height;
	StdDraw.setPenColor(colour);
	double [] listX = new double []
	{
		centerX,
		centerX-halfHeight,
		centerX-halfHeight,
		centerX-0.66*halfHeight,
		centerX-0.33*halfHeight,
		centerX,
		centerX+0.33*halfHeight,
		centerX+0.66*halfHeight,
		centerX+halfHeight,
		centerX+halfHeight,
	};
	double [] listY = new double []
	{
		centerY-halfHeight,
		centerY,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY,
		};
		StdDraw.filledPolygon(listX , listY);
}
/**
 * Draws a heart wherever you want with a default colour
 * @param x the x-coordinate of the center
 * @param y the y-coordinate of the center
 * @param height the height of the heart
 */
public static void drawHeart(double x, double y, double height)
{
	Utils.drawHeart(x, y, height, new Color(223 , 75 , 95));
}
}
