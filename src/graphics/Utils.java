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
}
