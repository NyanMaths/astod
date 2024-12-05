import java.nio.file.Path;


public class InvalidMapException extends Exception
{
	public InvalidMapException (Path mapLocation)
	{
		super(mapLocation + " is screwed !");
	}
}