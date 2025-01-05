package game;


/**
 * Exception to flag litteraly empty game file
 */
public class EmptyGameException extends Exception
{
/**
 * Only constructor of EmptyGameException
 */
public EmptyGameException ()
{
	super("this game is a bit empty, you should fill it with levels...");
}
}