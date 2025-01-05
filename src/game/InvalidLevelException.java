package game;

import java.nio.file.Path;


/**
 * Generic class for reporting level file issues
 */
public class InvalidLevelException extends Exception
{
/**
 * The invalid level's location
 */
private final Path location;

/**
 * Constructor for InvalidLevelException
 * @param levelLocation the corrupted level's path
 */
public InvalidLevelException (Path levelLocation)
{
	super(levelLocation + " is corrupted, probably a map issue");
	this.location = levelLocation;
}

/**
 * Getter for invalid level's location
 * @return the invalid level's path
 */
public Path getLocation ()
{
	return this.location;
}
}
