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
import map.NoEnemySpawnException;
import units.UninitializedSpawner;


public class Game
{
private final Queue<Level> levels;

public Game (String id) throws EmptyGameException, InvalidMapException, InvalidMapPathException, InvalidLevelException, NoEnemySpawnException
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
		this.levels.add(new Level(currentLevelName));

		currentLevelName = reader.readLine();
		while (currentLevelName != null)
		{
			this.levels.add(new Level(currentLevelName));
			currentLevelName = reader.readLine();
		}
	}
	catch (IOException eee)
	{
		System.err.println(eee);
	}
}
public void start () throws UninitializedSpawner
{
	Level currentLevel = this.levels.poll();

	while (currentLevel != null)
	{
		if (currentLevel.start())
		{
			System.out.println("yepeeeeee, you finished a level");
		}
		else
		{
			System.out.println("yikes, you died");
			return;
		}

		currentLevel = this.levels.poll();
	}

	// the player finished the game
	System.out.println("noice");
}
}