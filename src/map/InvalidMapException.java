package map;

import java.nio.file.Path;


public class InvalidMapException extends Exception
{
private final Path location;

public InvalidMapException (Path mapLocation)
{
	super(mapLocation + " is screwed !");
	this.location = mapLocation;
}

public Path getLocation ()
{
	return this.location;
}
}