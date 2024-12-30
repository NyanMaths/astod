package map;

import java.nio.file.Path;

public class NoEnemySpawnException extends Exception
{
    private final Path location;
    
    public NoEnemySpawnException (Path mapLocation)
    {
        super("No enemy spawn found !");
        this.location = mapLocation;
    }
    
    public Path getLocation ()
    {
        return this.location;
    }
}
