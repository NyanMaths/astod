package map;


/**
 * Flags a broken path in map
 */
public class InvalidMapPathException extends Exception
{
private static String parsePathError (PathError error)
{
	return switch (error)
	{
		case PathError.Loop -> "loop";
		case PathError.MultiplePath -> "multiple paths";
		case PathError.NoPath -> "no path";
	};
}

/**
 * Constructor for InvalidMapPathException
 * @param mapName the broken map
 * @param levelName the calling level
 * @param error the kind of path error
 */
public InvalidMapPathException (String mapName, String levelName, PathError error)
{
	super("map " + mapName + " in level " + levelName + " is broken. reason : " + parsePathError(error));
}
}
