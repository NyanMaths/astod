package map;

import java.util.List;


/**
 * Flags a multiple enemy spawn detection
 */
public class MultipleEnemySpawnException extends Exception
{
/**
 * Constructor for MultipleEnemySpawnException
 * @param mapName the faulty map
 * @param levelName the calling level identifier
 * @param spawns the list of enemy spawns, excpected to be at least of length 2
 */
public MultipleEnemySpawnException (String mapName, String levelName, List<Cell> spawns)
{
	super(generateErrorMessage(mapName, levelName, spawns));
}

/**
 * Just generates the error message for the class as Java cries about instructions before super()
 * @param mapName the faulty map
 * @param levelName the calling level identifier
 * @param spawns the list of enemy spawns, excpected to be at list of length 2
 * @return the message describing the exception
 */
private static String generateErrorMessage (String mapName, String levelName, List<Cell> spawns)
{
	String report = "multiple enemy spawns detected in " + mapName + "read in level " + levelName + " : ";
	for (Cell spawn : spawns)
	{
		report += "\nspawn at : " + spawn.getPosition();
	}

	return report;
}
}
