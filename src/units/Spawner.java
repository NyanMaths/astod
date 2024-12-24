package units;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import units.living.*;


/*
 * The object Spawner schedules the creation of new enemies and gives them to the game engine on the right time.
 */
public final class Spawner
{
private String waveName;
private PriorityQueue<LivingEntity> enemies;
private boolean ready;
private boolean active;

public Spawner (String waveName)
{
	this.ready = false;
	this.active = false;
	this.setWave(waveName);
}
public Spawner ()
{
	this.ready = false;
	this.active = false;
}

private LivingEntity getEntityFromName (String entityName) throws NonExistentEntity
{
	return switch (entityName)
	{
		case "Minion" -> new Minion(new Point2D.Float(0.0f, 0.0f));
		case "Fire Grognard" -> new FireGrognard(new Point2D.Float(0.0f, 0.0f));
		case "Wind Grognard" -> new WindGrognard(new Point2D.Float(0.0f, 0.0f));
		case "Earth Brute" -> new EarthBrute(new Point2D.Float(0.0f, 0.0f));
		case "Water Brute" -> new WaterBrute(new Point2D.Float(0.0f, 0.0f));
		case "Boss" -> new Boss(new Point2D.Float(0.0f, 0.0f));
		default -> throw new NonExistentEntity(entityName);
	};
}

/*
 * Load wave from assets. It only reads enemies, not their spawn timestamp yet.
 * This method nneds to be called before using the spawner unless you want an UninitializedSpawner exception kicking in
 * @return whether the wave is properly loaded or not
 */
public boolean setWave (String newWaveName)
{
	this.waveName = newWaveName;
	this.enemies = new PriorityQueue<>();
	Path wavePath = Paths.get("assets/waves/" + newWaveName + ".wve");

	try (BufferedReader reader = Files.newBufferedReader(wavePath))
	{
		String currentLine = reader.readLine();

		while (currentLine != null)
		{
			try
			{
				String[] entityData = currentLine.split("\\|");

				this.enemies.offer(getEntityFromName(entityData[1]));
			}
			catch (NonExistentEntity | IndexOutOfBoundsException eee)
			{
				this.enemies = new PriorityQueue<>();
				this.waveName = "";
				System.err.println(eee);
				return false;
			}

			currentLine = reader.readLine();
		}
	}
	catch (IOException eee)
	{
		System.err.println(eee);
	}

	this.ready = true;
	return true;
}

public String getWaveName () throws UninitializedSpawner
{
	if (!this.ready)
	{
		return this.waveName;
	}
	
	throw new UninitializedSpawner();
}

public boolean isActive ()
{
	return this.active;
}


public void start () throws UninitializedSpawner
{
	if (!this.ready)
	{
		throw new UninitializedSpawner();
	}

	// todo : schedule spawn tasks

	this.active = true;
}
}
