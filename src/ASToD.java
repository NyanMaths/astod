import libraries.StdDraw;
import java.awt.Color;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	map.Map map = new map.Map("10-10");

	StdDraw.setCanvasSize(1024, 720);
    StdDraw.setXscale(-12, 1012);
    StdDraw.setYscale(-10, 710);

    int rowsCount = map.getRowsCount();
    int columnsCount = map.getColumnsCount();
    double cellSize = Math.min(1024.0, 720.0) / (double)Math.max(rowsCount, columnsCount);

	for (int i = 0 ; i<rowsCount ; i++)
	{
		for (int j = 0 ; j<columnsCount ; j++)
		{
			map.Cell currentCell = map.getCell(i, j);
			StdDraw.setPenColor(currentCell.getColor());

			double x = cellSize*i + 0.5*cellSize;
			double y = cellSize*j + 0.5*cellSize;
			StdDraw.filledSquare(x, y, cellSize*0.5f);
		}
	}
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
