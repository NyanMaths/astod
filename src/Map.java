import java.nio.file.Path;
import java.nio.file.Paths;
import libraries.StdDraw;

public class Map
{
private final Path location;
private Cell[][] matrix;

public Map (String name)
{
	this.location = Paths.get("assets/maps/" + name + "mtp");
}
}

