import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Map
{
private final Path location;
private Cell[][] matrix;

public Map (String name)
{
	this.location = Paths.get("assets/maps/" + name + "mtp");
}
}

/* essais numéro 65485154415415
 * Tentative de lecture de fichier : ECHEC
 * MEME AVEC LE PTN DE CODE DU PROF RIEN NE SE PASSE
 * DROP
 * BREAK
 * KILL
 */

