package units;

import game.Level;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import units.living.*;


/*
 * The object Spawner schedules the creation of new enemies and gives them to the game engine on the right time.
 */
public final class Spawner
{
private final Level level;
private String waveName;
private Queue<LivingEntity> enemies;
private Queue<Long> spawnDelays;
private boolean ready;
private boolean active;
private Point2D.Float position;

private final Timer scheduler;


public Spawner (Level parent)
{
	this.level = parent;
	this.ready = false;
	this.active = false;
	this.scheduler = new Timer();
}

private LivingEntity getEntityFromName (String entityName) throws NonExistentEntity
{
	return switch (entityName)
	{
		case "Minion" -> new Minion((Point2D.Float)this.position.clone());
		case "Fire Grognard" -> new FireGrognard((Point2D.Float)this.position.clone());
		case "Wind Grognard" -> new WindGrognard((Point2D.Float)this.position.clone());
		case "Earth Brute" -> new EarthBrute((Point2D.Float)this.position.clone());
		case "Water Brute" -> new WaterBrute((Point2D.Float)this.position.clone());
		case "Boss" -> new Boss((Point2D.Float)this.position.clone());
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
	this.enemies = new LinkedList<>();
	this.spawnDelays = new LinkedList<>();
	Path wavePath = Paths.get("assets/waves/" + newWaveName + ".wve");

	try (BufferedReader reader = Files.newBufferedReader(wavePath))
	{
		String currentLine = reader.readLine();
		long cumSum = 0;  // skull emoji

		while (currentLine != null)
		{
			try
			{
				String[] entityData = currentLine.split("\\|");

				long currentSpawnTime = (long)(Double.parseDouble(entityData[0]) * 1000);
				this.spawnDelays.offer(currentSpawnTime - cumSum);
				cumSum = currentSpawnTime;
				this.enemies.offer(getEntityFromName(entityData[1]));
			}
			catch (NonExistentEntity | IndexOutOfBoundsException eee)
			{
				this.enemies = new LinkedList<>();
				this.spawnDelays = new LinkedList<>();
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

public void setPosition (Point2D.Float newPosition)
{
	this.position = newPosition;
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


/*
 * Spawn the next enemy then start the timer for next
 */
public void spawnNext ()
{
	this.level.spawn(this.enemies.poll());

	if (this.enemies.peek() != null)
	{
		this.scheduler.schedule(new SpawnTask(this), this.spawnDelays.poll());
	}
	else
	{
		this.active = false;
	}
}

public void start () throws UninitializedSpawner
{
	if (!this.ready)
	{
		throw new UninitializedSpawner();
	}

	this.scheduler.schedule(new SpawnTask(this), this.spawnDelays.poll());

	this.active = true;
}
}
