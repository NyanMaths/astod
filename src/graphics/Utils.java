package graphics;

import java.awt.Color;
import libraries.StdDraw;
import units.Element;


public class Utils
{
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
public static void drawHeart(double x, double y, double height)
{
	Utils.drawHeart(x, y, height, new Color(223 , 75 , 95));
}
}
