package map;

import java.nio.file.Path;


/**
 * Flags a corrupted map
 */
public class InvalidMapException extends Exception
{
/**
 * The location of the corrupted map
 */
private final Path location;

/**
 * Constructor for InvalidMpaException
 * @param mapLocation the corrupted map's path
 */
public InvalidMapException (Path mapLocation)
{
	super(mapLocation + " is funky !");
	this.location = mapLocation;
}

/**
 * Getter for corrupted map's path
 * @return the corrupted map's path
 *
 */
public Path getLocation ()
{
	return this.location;
}
}
