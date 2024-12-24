import java.nio.file.Path;


public class InvalidLevelException extends Exception 
{
private final Path location;

public InvalidLevelException (Path levelLocation)
{
	super(levelLocation + " is screwed !");
	this.location = levelLocation;
}

public Path getLocation ()
{
	return this.location;
}
}
