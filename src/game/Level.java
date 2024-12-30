package game;

import graphics.LevelUI;
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
import java.util.stream.Collectors;
import libraries.StdDraw;
import map.InvalidMapException;
import map.InvalidMapPathException;
import map.Map;
import map.NoEnemySpawnException;
import units.Element;
import units.Spawner;
import units.UninitializedSpawner;
import units.Unit;
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


public Level (String levelName) throws InvalidMapException, InvalidMapPathException, InvalidLevelException, NoEnemySpawnException
{
	this.map = new Map();
	this.spawner = new Spawner(this);
	this.enemies = new ArrayList<>();
	this.towers = new ArrayList<>();
	this.player = new Player(System.getProperty("user.name", "Player"), Element.Neutral, 100, 50, 100);
	this.UI = new LevelUI(this.player);

	this.load(levelName);
}

public void load (String levelName) throws InvalidMapException, InvalidMapPathException, InvalidLevelException, NoEnemySpawnException
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
 * @return the nearest in-range enemy from the unit's side
 */
public Unit getNearestEnemy (Unit unit, float maxDistance)
{
	List<Unit> nearbyEnemies = (List)(List) (unit.isAttacker() ? this.towers : this.enemies)  // no primitive switch ? no dxvk ?
								.stream().filter(enemy->unit.getPosition().distance(((Unit)enemy).getPosition()) <= maxDistance).collect(Collectors.toList());
	// remember kids : don't write unreadable code unless you want to never be replaced because no one would want to maintain your hideous thingy

	Unit currentNearest = null;

	for (Unit currentEnemy : nearbyEnemies)
	{
		if (currentNearest == null)
		{
			currentNearest = currentEnemy;
		}
		else if (unit.getPosition().distance(currentNearest.getPosition()) > unit.getPosition().distance(currentEnemy.getPosition()))
		{
			currentNearest = currentEnemy;
		}
	}

	return currentNearest;
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

public void buildTower (Point2D.Float position)
{
	Tower newTower = player.buy(this.UI.takeTowerName(), position);

	if (newTower == null)
	{
		System.err.println("you are poor");
		return;
	}

	this.towers.add(newTower);
	System.err.println("spawned new " + this.towers.getLast().getName() + " at :" + position);
	Point2D.Float cellPos =  this.map.getCellCoordinates(position);
	this.map.getCell(cellPos).toggleOccupied();
}


/*
 * Ticks one wave, this method exists for the sole purpose of reducing indentation level of the game loop.
 * This also is a testing zone
 * @return whether the player was victorious or not
 */
public boolean startWave ()
{
	int test = 0;
	while (this.player.isAlive() || this.spawner.isActive())  // E
	{
		try
		{
		StdDraw.clear();
		map.draw();
		this.drawEntities();
		this.UI.draw();
		if(test==0) 
		{
			this.map.listCells();
			test++;
		}
		StdDraw.show();  // finalize draw
		//StdDraw.pause(20);
		/*//Aniation stuff
		int rowsCount = map.getRowsCount();
		int columnsCount = map.getColumnsCount();
		double cellSize = Math.min(1024.0, 720.0) / (double)Math.max(rowsCount, columnsCount);
		double x = cellSize*i + 0.5*cellSize;
		double y = cellSize*i + 0.5*cellSize;
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.filledSquare(x+i,y+i,cellSize);*/


		if (StdDraw.isMousePressed())
		{
			Point2D.Float position = new Point2D.Float((float)StdDraw.mouseX(), (float)StdDraw.mouseY());

			if (this.UI.isShopPressed())
			{
				this.UI.setSpawningTower(this.UI.getPressedTowerType());  // cancel spawn if the player clicks on a blank area of the shop, easier to code and better for the game
			}
			else if (this.UI.isBuildingTower())
			{
				if (map.isBuildable(position))
				{
					buildTower(position);
				}
				else
				{
					System.err.println("unable to build here");
				}
			}
		}
		}
		catch (java.util.ConcurrentModificationException eee)
		{
			// dunno how to fix concurrent accesses to entities list between display and game, they are ignored for now as they just stall display for a bit
		}
	}

	return this.player.isAlive();
}
}
