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


/**
 * The object Spawner schedules the creation of new enemies and gives them to the game engine at the right time.
 */
public final class Spawner
{
/**
 * The level to submit the spawned enemies
 */
private final Level level;
/**
 * The loaded wave name
 */
private String waveName;
/**
 * the enemies to submit
 */
private Queue<LivingEntity> enemies;
/**
 * The spawn delays between units
 */
private Queue<Long> spawnDelays;
/**
 * The wellness of the spawner
 */
private boolean ready;
/**
 * Is the spawner spawning ?
 */
private boolean active;
/**
 * The spawn position of the the units
 */
private Point2D.Float position;

/**
 * The spawn scheduler of the spawner
 */
private final Timer scheduler;


/**
 * Constructor for Spawner
 * @param parent the level to submit enemies to
 */
public Spawner (Level parent)
{
	this.level = parent;
	this.ready = false;
	this.active = false;
	this.scheduler = new Timer();
}

/**
 * Parser for spawning units from their pretty names
 * @param entityName the type of the unit
 * @return the newly created unit
 * @throws NonExistentEntity if the name is incorrect
 */
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

/**
 * Loads wave from assets.
 * This method needs to be called before using the spawner unless you want an UninitializedSpawner exception kicking in
 * @param newWaveName the wave to load
 * @return whether the wave is properly loaded or not
 */
public boolean setWave (String newWaveName)
{
	this.ready = false;
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
	return this.ready;
}

/**
 * Setter for spawner's position
 * @param newPosition the new position to spawn units on
 */
public void setPosition (Point2D.Float newPosition)
{
	this.position = newPosition;
}

/**
 * Getter for current wave name
 * @return the current wave name
 * @throws UninitializedSpawner if no wave is loaded
 */
public String getWaveName () throws UninitializedSpawner
{
	if (!this.ready)
	{
		throw new UninitializedSpawner();
	}

	return this.waveName;
}

/**
 * Is the spawner spawning ?
 * @return whether the spawner is spawning units or not
 */
public boolean isActive ()
{
	return this.active;
}


/**
 * Spawns the next enemy then starts the timer for next
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

/**
 * Starts the spawner schedule
 * @throws UninitializedSpawner if the spawner does not hold a wave
 */
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
