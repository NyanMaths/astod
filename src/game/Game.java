package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import map.InvalidMapException;
import map.InvalidMapPathException;
import map.MultipleEnemySpawnException;
import map.NoEnemySpawnException;
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
 */
public Game (String id) throws EmptyGameException
{
	Path location = Paths.get("assets/games/game" + id + ".g");
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
 * @throws InvalidMapPathException in case of unreadable map's path
 * @throws InvalidLevelException if the level file is corrupted
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is too much enemy spawns
 */
public void start () throws UninitializedSpawner, InvalidMapException, InvalidMapPathException, InvalidLevelException, NoEnemySpawnException, MultipleEnemySpawnException
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
	System.out.println("noice");
}
}