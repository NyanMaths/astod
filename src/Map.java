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

public  void readFile(Path location)
{
	try (BufferedReader reader = Files.newBufferedReader(this.location))
	{
		String ligne = null;  
        while ((ligne = reader.readLine()) != null) 
		{  
			System.out.println(ligne);  
        }  
        }
		catch (IOException e) 
		{
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}

