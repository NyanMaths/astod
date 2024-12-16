import libraries.StdDraw;
import java.awt.Color;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	map.Map map = new map.Map("10-10");
	map.draw(map);
	StdDraw.enableDoubleBuffering();
	while(true)
	{
		StdDraw.setPenColor(Color.BLACK);
		if(StdDraw.isMousePressed())
		StdDraw.point(StdDraw.mouseX(), StdDraw.mouseY());
		StdDraw.show();
	}
}
}
