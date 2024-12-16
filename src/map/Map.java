package map;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import libraries.StdDraw;

public final class Map
{
private Path name;
private List<List<Cell>> matrix;

public Map (String name) throws InvalidMapException
{
	this.loadFromFile(name);
}

public void loadFromFile (String name) throws InvalidMapException
{
	this.name = Paths.get("assets/maps/" + name + ".mtp");
	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.name))
	{
		String currentLine = reader.readLine();

		while (currentLine != null)
		{
			this.matrix.addLast(new ArrayList<>());
			for (Character cellType : currentLine.toCharArray())
			{
				this.matrix.getLast().addLast(new Cell(cellType));
			}
			
			currentLine = reader.readLine();
		}
    }
	catch (IOException eee)
	{
		System.err.println(eee);
	}
}


public Path getLocation ()
{
	return this.name;
}

public int getRowsCount ()
{
	return matrix.size();
}

public int getColumnsCount ()
{
	return matrix.get(0).size();
}

public Cell getCell(int row, int col)
{
	return this.matrix.get(row).get(col);
}

public void draw(Map map)
{
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
}
}
