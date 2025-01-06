package game;

import java.nio.file.Path;


/**
 * Thrown when the asked games does not exist
 */
public class NoSuchGameException extends Exception
{
/**
 * Constructor for NoSuchGameException
 * @param gamePath the non existent game's path
 */
public NoSuchGameException (Path gamePath)
{
	super("game " + gamePath + " does not exist or is a folder");
}
}