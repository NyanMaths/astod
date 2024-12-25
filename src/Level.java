import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import units.Element;
import libraries.StdDraw;
import map.InvalidMapException;
import map.InvalidMapPathException;
import map.Map;
import units.Spawner;
import units.UninitializedSpawner;
import units.Unit;
import units.living.LivingEntity;
import units.living.WindGrognard;


public final class Level
{
private Queue<String> waves;
private final Map map;
private final Spawner spawner;

private final Player player;


public Level (String levelName) throws InvalidMapException, InvalidMapPathException, InvalidLevelException
{
	this.map = new Map();
	this.spawner = new Spawner();
	this.player = new Player(System.getProperty("user.name", "Player"), Element.Neutral, 100, 100, 50);

	this.load(levelName);
}

public void load (String levelName) throws InvalidMapException, InvalidMapPathException, InvalidLevelException
{
	Path location = Paths.get("assets/levels/level" + levelName + ".lvl");
	this.waves = new LinkedList<>();

	try (BufferedReader reader = Files.newBufferedReader(location))
	{
		// first get the level's map
		String currentLine = reader.readLine();
		if (currentLine.isEmpty())
		{
			throw new InvalidLevelException(location);
		}
		this.map.load(currentLine);

		// then get all waves, possibly none, should result in instant victory
		currentLine = reader.readLine();
		while (currentLine != null)
		{
			this.waves.add(currentLine);
			currentLine = reader.readLine();
		}
	}
	catch (IOException eee)
	{
		System.err.println(eee);
	}
}


/*
 * Starts the level : spawns enemies and ticks the game.
 * Basically the main game loop
 * @return whether the player is victorious or not
 */
public boolean start () throws UninitializedSpawner
{
	String wave = this.waves.poll();

	while (wave != null)
	{
		this.spawner.setWave(wave);
		this.spawner.start();
		List<LivingEntity> enemies = new ArrayList<>();

		if (this.startWave() == false)
		{
			return false;
		}
		
		wave = this.waves.poll();
	}

	// the player made it though all waves
	return true;
}


/*
 * Ticks one wave, this method exists for the sole purpose of reducing indentation level of the game loop.
 * This also is a testing zone
 * @return whether the player was victorious or not
 */
public boolean startWave ()
{
	Unit testUnit = new WindGrognard(new Point2D.Float(100, 100));

	while (this.spawner.isActive())
	{
	this.map.draw();

	//Aniation stuff
	int rowsCount = map.getRowsCount();
	int columnsCount = map.getColumnsCount();
	double cellSize = Math.min(1024.0, 720.0) / (double)Math.max(rowsCount, columnsCount);
	StdDraw.enableDoubleBuffering();
	for(double i = 0; true; i += 0.02)
	{
		double x = cellSize*i + 0.5*cellSize;
		double y = cellSize*i + 0.5*cellSize;
		StdDraw.clear();
		map.draw();
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(x+i,y+i,cellSize);
		testUnit.draw();
		StdDraw.show();
		StdDraw.pause(20);
	}

	//Drawning in real time stuff

	/*while(true)
	{
		StdDraw.setPenColor(Color.BLACK);
		if(StdDraw.isMousePressed())
		StdDraw.point(StdDraw.mouseX(), StdDraw.mouseY());
		StdDraw.show();
	}*/
	}

	return true;
}
}
