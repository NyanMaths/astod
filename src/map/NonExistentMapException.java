package map;

import java.nio.file.Path;


/**
 * Flags a unreadable map location
 */
public class NonExistentMapException extends Exception
{
/**
 * The unreadable map's location
 */
private final Path location;

/**
 * Constructor for NonExistentMapException
 * @param mapLocation the unreadable map's location
 */
public NonExistentMapException (Path mapLocation)
{
	super(mapLocation + " does not exist !");
	this.location = mapLocation;
}

/**
 * Getter for unreadable map's location
 * @return the unreadable map's location
 */
public Path getLocation ()
{
	return this.location;
}
}
