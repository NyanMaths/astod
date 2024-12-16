import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.Unit;
import units.living.WindGrognard;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
	map.Map map = new map.Map("10-10");
	map.draw();

	Unit testUnit = new WindGrognard(new Point2D.Float(100, 100));

	//Aniation stuff
	int rowsCount = map.getRowsCount();
    int columnsCount = map.getColumnsCount();
    double cellSize = Math.min(1024.0, 720.0) / (double)Math.max(rowsCount, columnsCount);
	StdDraw.enableDoubleBuffering();
	for(double i = 0; true; i += 0.02)
	{
		double x = cellSize*i + 0.5*cellSize;
		double y = cellSize*i + 0.5*cellSize;
		StdDraw.clear();
		map.draw();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(x+i,y+i,cellSize);
		testUnit.draw();
		StdDraw.show();
		StdDraw.pause(20);
	}

	//Drawning in real time stuff

	/*while(true)
	{
		StdDraw.setPenColor(Color.BLACK);
		if(StdDraw.isMousePressed())
		StdDraw.point(StdDraw.mouseX(), StdDraw.mouseY());
		StdDraw.show();
	}*/
}
}
