package units.towers;


/**
 * Thrown if there is a tower on the selected tile to build on
 */
public class TileOccupiedException extends Exception
{
/**
 * Constructor for TileOccupiedException
 */
public TileOccupiedException ()
{
	super("tile already occupied by a tower");
}
}
