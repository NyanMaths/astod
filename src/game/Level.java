package game;

import graphics.LevelUI;
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
import libraries.StdDraw;
import map.InvalidMapException;
import map.InvalidMapPathException;
import map.Map;
import units.Element;
import units.Spawner;
import units.UninitializedSpawner;
import units.living.LivingEntity;
import units.towers.Tower;


public final class Level
{
private Queue<String> waves;
private final Map map;
private final Spawner spawner;
private final List<LivingEntity> enemies;
private final List<Tower> towers;

private final Player player;

private final LevelUI UI;


public Level (String levelName) throws InvalidMapException, InvalidMapPathException, InvalidLevelException
{
	this.map = new Map();
	this.spawner = new Spawner(this);
	this.enemies = new ArrayList<>();
	this.towers = new ArrayList<>();
	this.player = new Player(System.getProperty("user.name", "Player"), Element.Neutral, 100, 50, 100);
	this.UI = new LevelUI(this.player);

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

		this.spawner.setPosition(this.map.getSpawnerPosition());
		this.player.setPosition(this.map.getPlayerPosition());

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
 * Adds the newly spawned enemy into battle
 */
public void spawn (LivingEntity enemy, Spawner funnyToken)
{
	this.enemies.add(enemy);
}


/*
 * Draws enemies, towers then player from bottom to top layer.
 * Can raise ConcurrentModificationException for funny reasons.
 */
private void drawEntities ()
{
	this.enemies.stream().forEach(enemy->enemy.draw());
	this.towers.stream().forEach(tower->tower.draw());
	this.player.draw();
}


/*
 * Ticks one wave, this method exists for the sole purpose of reducing indentation level of the game loop.
 * This also is a testing zone
 * @return whether the player was victorious or not
 */
public boolean startWave ()
{
	for (double i = 0.0 ; this.player.isAlive() || this.spawner.isActive() ; i += 0.02)  // E
	{
		try
		{
		StdDraw.clear();
		map.draw();
		this.drawEntities();
		this.UI.draw();

		//Aniation stuff
		int rowsCount = map.getRowsCount();
		int columnsCount = map.getColumnsCount();
		double cellSize = Math.min(1024.0, 720.0) / (double)Math.max(rowsCount, columnsCount);
		double x = cellSize*i + 0.5*cellSize;
		double y = cellSize*i + 0.5*cellSize;
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(x+i,y+i,cellSize);

		// finalize draw
		StdDraw.show();
		StdDraw.pause(20);
		//System.out.println("Shop clicked : " + map.isShopClicked()); //test si shop est cliqué : REUSSI
		//System.out.println("Map cliced : " +map.isMapClicked()); //pareil pour la map : REUSSI
		System.out.println("Tower : " + map.whichTower()); //test pour savoir quel tour est choisi : REUSSI
		//System.out.println("Cell" + map.whereInMatrix(StdDraw.mouseX(),StdDraw.mouseY())); //test pour savoir quelle cellule est cliquée : REUSSI
		/*if (StdDraw.isMousePressed())
		{
			System.out.println("Buildable? " + map.isBuildable(new Point2D.Float((float)StdDraw.mouseX(), (float)StdDraw.mouseY())));
		}*/
		}
		catch (java.util.ConcurrentModificationException eee)  // get fucked haha
		{
		}
	}

	return this.player.isAlive();
}
}
