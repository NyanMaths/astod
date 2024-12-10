import libraries.StdDraw;

public class ASToD
{
public static void main (String[] args) throws Exception
{
	map.Map map = new map.Map("5-8");

	StdDraw.setCanvasSize(1600, 900);

	int rowsCount = map.getRowsCount();
	int columnsCount = map.getColumnsCount();
	double cellSize = 1.0 / (double)Math.max(rowsCount, columnsCount);

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
}
}
