import libraries.StdDraw;
import java.awt.Color;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);
	map.Map map = new map.Map("10-10");
	map.draw(map);

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
		map.draw(map);
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(x+i,y+i,cellSize);
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
