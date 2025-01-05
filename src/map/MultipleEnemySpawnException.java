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
 * @param spawns the list of enemy spawns, excpected to be at list of length 2
 */
public MultipleEnemySpawnException (String mapName, String levelName, List<Cell> spawns)
{
	final String report = "multiple enemy spawns detected :";
	spawns.stream().forEachOrdered(spawn->report.concat("\nspawn at : "+spawn.getPosition()));

	super(report);
}
}
