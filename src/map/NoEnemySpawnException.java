package map;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Flags a map without enemy spawn
 */
public class NoEnemySpawnException extends Exception
{
/**
 * The faulty map's location
 */
private final Path location;

/**
 * Constructor for NoEnemySpawnException
 * @param mapName the faulty map's name
 * @param levelName the calling level
 */
public NoEnemySpawnException (String mapName, String levelName)
{
    super("no enemy spawn found in level " + levelName + " for map" + mapName);
    this.location = Paths.get("asstes/maps/" + mapName + ".mtp");
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
