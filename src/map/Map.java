package map;

import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import libraries.StdDraw;


public final class Map implements Drawable
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

		int i = 0;
		while (currentLine != null)
		{
			this.matrix.addLast(new ArrayList<>());
			int j = 0;
			for (Character cellType : currentLine.toCharArray())
			{
				this.matrix.getLast().addLast(new Cell(cellType, new Point2D.Float(i, j)));
				j++;
			}
			
			currentLine = reader.readLine();
			i++;
		}

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));
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

public Cell getCell (int row, int col)
{
	return this.matrix.get(row).get(col);
}

@Override
public void draw ()
{
    int rowsCount = this.getRowsCount();
    int columnsCount = this.getColumnsCount();

	for (int i = 0 ; i<rowsCount ; i++)
	{
		for (int j = 0 ; j<columnsCount ; j++)
		{
			this.getCell(i, j).draw();
		}
	}
	
	StdDraw.setPenColor(Color.RED);
	StdDraw.filledRectangle(865,688,144,12);
	StdDraw.setPenColor(Color.GREEN);
	StdDraw.filledRectangle(865,641,144,25);
	StdDraw.setPenColor(Color.PINK);
	StdDraw.filledRectangle(865, 303, 144,303);
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.text(865, 688, "Level");
	StdDraw.text(865, 641, "Player");
	StdDraw.text(865, 303, "Store");
}
}
