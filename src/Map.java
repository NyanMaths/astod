import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class Map
{
private Path location;
private List<List<Cell>> matrix;

public Map (Path location) throws InvalidMapException
{
	this.loadFromFile(location);
}

public void loadFromFile (Path location) throws InvalidMapException
{
	this.location = Paths.get("assets/maps/" + location + ".mtp");
	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.location))
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
	return this.location;
}
}
