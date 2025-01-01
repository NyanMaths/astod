package game;


public class EmptyGameException extends Exception
{
public EmptyGameException ()
{
	super("this game is a bit empty, you should fill it with levels...");
}
}