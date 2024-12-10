package map;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
}
