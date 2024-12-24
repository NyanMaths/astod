package map;

import java.nio.file.Path;


public class InvalidMapPathException extends Exception
{
private final Path location;

public InvalidMapPathException (Path mapLocation)
{
	super(mapLocation + " does not exist !");
	this.location = mapLocation;
}

public Path getLocation ()
{
	return this.location;
}
}
