package units;


/**
 * When the spawner is called without a proper wave loaded
 */
public class UninitializedSpawner extends Exception
{
/**
 * Constructor for UninitializedSpawner
 */
public UninitializedSpawner ()
{
	super("the spawner does not hold a proper enemies wave");
}
}
