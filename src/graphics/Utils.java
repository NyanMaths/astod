package graphics;

import java.awt.Color;
import game.Player;
import libraries.StdDraw;
import units.Element;


public class Utils
{

private final Player player;

public Utils (Player player)
{
	this.player = player;
}

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
public void drawHeart(double x, double y, double height)
{
	double centerX = 930;
	double centerY = 641;
	double halfHeight = height;
	StdDraw . setPenColor ( new Color (223 , 75 , 95) );
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
}
