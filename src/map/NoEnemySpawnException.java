package map;

import java.nio.file.Path;
import java.nio.file.Paths;


public class NoEnemySpawnException extends Exception
{
    private final Path location;

    public NoEnemySpawnException (String mapName, String levelName)
    {
        super("no enemy spawn found in level " + levelName + " for map" + mapName);
        this.location = Paths.get("asstes/maps/" + mapName + ".mtp");
    }

    public Path getLocation ()
    {
        return this.location;
    }
}
