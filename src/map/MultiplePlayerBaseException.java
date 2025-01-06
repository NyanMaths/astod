package map;

import java.util.List;


/**
 * Flags a multiple player base detection
 */
public class MultiplePlayerBaseException extends Exception
{
/**
 * Constructor for MultiplePlayerBaseException
 * @param mapName the faulty map
 * @param levelName the calling level identifier
 * @param bases the list of player's bases, excpected to be at least of length 2
 */
public MultiplePlayerBaseException (String mapName, String levelName, List<Cell> bases)
{
	super(generateErrorMessage(mapName, levelName, bases));
}

/**
 * Just generates the error message for the class as Java cries about instructions before super()
 * @param mapName the faulty map
 * @param levelName the calling level identifier
 * @param bases the list of player's bases, excpected to be at list of length 2
 * @return the message describing the exception
 */
private static String generateErrorMessage (String mapName, String levelName, List<Cell> bases)
{
	String report = "multiple player bases detected in " + mapName + " called from level " + levelName + " : ";
	for (Cell base : bases)
	{
		report += "\nbase at : " + base.getPosition();
	}

	return report;
}
}
