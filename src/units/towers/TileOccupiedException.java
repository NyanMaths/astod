package units.towers;


public class TileOccupiedException extends Exception
{
public TileOccupiedException ()
{
	super("tile already occupied by a tower");
}
}