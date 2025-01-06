package map;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Flags a map without a base
 */
public class NoPlayerBaseException extends Exception
{
/**
 * The faulty map's location
 */
private final Path location;

/**
 * Constructor for NoPlayerBaseException
 * @param mapName the faulty map's name
 * @param levelName the calling level
 */
public NoPlayerBaseException (String mapName, String levelName)
{
    super("no player base found in level " + levelName + " for map " + mapName);
    this.location = Paths.get("assets/maps/" + mapName + ".mtp");
}

/**
 * Getter for the faulty map's location
 * @return the faulty map's location
 */
public Path getLocation ()
{
    return this.location;
}
}
