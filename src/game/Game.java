package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import map.InvalidMapException;
import map.MultipleEnemySpawnException;
import map.MultiplePlayerBaseException;
import map.NoEnemySpawnException;
import map.NoPlayerBaseException;
import map.NonExistentMapException;
import units.UninitializedSpawner;


/**
 * Initiates the game and loops through levels
 */
public class Game
{
/**
 * Levels to be played
 */
private final Queue<String> levels;

/**
 * Load and validate the game's levels
 * @param id the game's id, see assets/games to add more
 * @throws EmptyGameException if there is nothing in the game, that would be lame
 * @throws NoSuchGameException when the provided game does not exist
 */
public Game (String id) throws EmptyGameException, NoSuchGameException
{
	Path location = Paths.get("assets/games/game" + id + ".g");
	File levelFile = new File("assets/games/game" + id + ".g");
	if (levelFile.isDirectory() || !levelFile.exists()) throw new NoSuchGameException(location);

	this.levels = new LinkedList<>();

	try (BufferedReader reader = Files.newBufferedReader(location))
	{
		// first get the game's level
		String currentLevelName = reader.readLine();
		if (currentLevelName.isEmpty())
		{
			throw new EmptyGameException();
		}
		this.levels.add(currentLevelName);

		currentLevelName = reader.readLine();
		while (currentLevelName != null)
		{
			this.levels.add(currentLevelName);
			currentLevelName = reader.readLine();
		}
	}
	catch (IOException eee)
	{
		System.err.println(eee);
	}
}

/**
 * Start the game loop : loops through each level and each levels loops through their enemies waves
 * @throws UninitializedSpawner if the spawner could not load its waves
 * @throws InvalidMapException in case of corrupted map
 * @throws InvalidLevelException if the level file is corrupted
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is too much enemy spawns
 * @throws NoPlayerBaseException if there is no base for the player
 * @throws MultiplePlayerBaseException if there is too much bases for player
 * @throws NonExistentMapException in case of unreadable map's path
 */
public void start () throws UninitializedSpawner, InvalidMapException, InvalidLevelException, NoEnemySpawnException, MultipleEnemySpawnException, NonExistentMapException, MultiplePlayerBaseException, NoPlayerBaseException
{
	while (this.levels.peek() != null)
	{
		Level currentLevel = new Level(this.levels.poll());

		if (currentLevel.start())
		{
			System.out.println("yepeeeeee, you finished a level");
		}
		else
		{
			System.out.println("yikes, you died");
			return;
		}
	}

	// the player finished the game
	System.out.println("noice, you win");
}
}