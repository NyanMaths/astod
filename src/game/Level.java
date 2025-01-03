package game;

import graphics.LevelUI;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import libraries.StdDraw;
import map.InvalidMapException;
import map.InvalidMapPathException;
import map.Map;
import map.NoEnemySpawnException;
import units.AttackMode;
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
private final List<Unit> enemies;
private final List<Unit> towers;

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
	Path location = Paths.get("assets/levels/" + levelName + ".lvl");
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
		Unit.setMap(this.map);

		this.spawner.setPosition(this.map.getSpawnerPosition());
		this.player.setPosition(this.map.getPlayerPosition());

		// then get all waves, possibly none, this case should result in instant victory
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


private List<Unit> getNearbyEnemies (Unit unit, float maxDistance)
{
	List<Unit> nearbyEnemies = unit.isAttacker() ? this.towers : this.enemies;
	if (maxDistance <= 0.001)
	{
		nearbyEnemies = nearbyEnemies.stream().filter(enemy->unit.getPosition().distance(((Unit)enemy).getPosition()) <= maxDistance).collect(Collectors.toList());
	}
	// remember kids : don't write unreadable code unless you want to never be replaced because no one would want to maintain your hideous thingy

	return nearbyEnemies;
}

/*
 * Set maxDistance to 0 to get unlimited range
 * @return the nearest unit respecting the given filter
 */
public Unit getNearest (Unit unit, float maxDistance, AttackMode filter)
{
	List<Unit> nearbyEnemies = this.getNearbyEnemies(unit, maxDistance);

	Unit currentTarget = null;

	for (Unit currentEnemy : nearbyEnemies)
	{
		if (currentTarget == null)
		{
			currentTarget = currentEnemy;
		}
		else
		{
		switch (filter)
		{
			case AttackMode.Nearest ->
			{
				if (unit.distance(currentTarget) > unit.distance(currentEnemy))
				{
					currentTarget = currentEnemy;
				}
			}
			case AttackMode.Healthiest ->
			{
				if (currentEnemy.getHealth() > currentTarget.getHealth())
				{
					currentTarget = currentEnemy;
				}
				else if (currentEnemy.getHealth() == currentTarget.getHealth() && unit.distance(currentTarget) > unit.distance(currentEnemy))
				{
					currentTarget = currentEnemy;
				}
			}
			case AttackMode.MostWounded ->
			{
				if (currentEnemy.getHealth() < currentTarget.getHealth())
				{
					currentTarget = currentEnemy;
				}
				else if (currentEnemy.getHealth() == currentTarget.getHealth() && unit.distance(currentTarget) > unit.distance(currentEnemy))
				{
					currentTarget = currentEnemy;
				}
			}
			case AttackMode.Strongest ->
			{
				if (currentEnemy.getAttack() > currentTarget.getAttack())
				{
					currentTarget = currentEnemy;
				}
				else if (currentEnemy.getAttack() == currentTarget.getAttack() && unit.distance(currentTarget) > unit.distance(currentEnemy))
				{
					currentTarget = currentEnemy;
				}
			}
			case AttackMode.Weakest ->
			{
				if (currentEnemy.getAttack() < currentTarget.getAttack())
				{
					currentTarget = currentEnemy;
				}
				else if (currentEnemy.getAttack() == currentTarget.getAttack() && unit.distance(currentTarget) > unit.distance(currentEnemy))
				{
					currentTarget = currentEnemy;
				}
			}
		}
		}
	}

	return currentTarget;
}


/*
 * Adds the newly spawned enemy into battle
 */
public void spawn (LivingEntity enemy, Spawner funnyToken)
{
	this.enemies.add(enemy);
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
	Point2D.Float cellPos =  this.map.getCellCoordinates(position);
	this.map.getCell(cellPos).toggleOccupied();
}
/*
 * Yeet a unit from the game and make it woe (LOUD)
 */
public void blight (Unit unit)
{
	if (unit.isAttacker())
	{
		this.player.reward((LivingEntity)unit);
		this.enemies.remove(unit);
	}
	else
	{
		this.towers.remove(unit);
	}

	try
	{
		File f = new File("assets/woe.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
	}
	catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
	{
		System.err.println(e.getMessage());
	}
}
public void slapPlayer (LivingEntity enemy)
{
	this.player.hurt(enemy.getAttack());
	this.enemies.remove(enemy);

	try
	{
		File f = new File("assets/woooooo.wav");
		AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
		Clip clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
	}
	catch (IOException | LineUnavailableException | UnsupportedAudioFileException e)
	{
		System.err.println(e.getMessage());
	}
}


private static void tick (Unit unit)
{
	if (unit != null)
	{
		unit.tick();
	}
}
private void tickEntities ()
{
	this.enemies.stream().forEach(enemy->tick(enemy));
	this.towers.stream().forEach(tower->tower.tick());
}


/*
 * Draw enemies, towers then player from bottom to top layer.
 * Can raise ConcurrentModificationException for funny reasons.
 */
private void drawEntities ()
{
	this.enemies.stream().forEach(enemy->enemy.draw());
	this.towers.stream().forEach(tower->tower.draw());
	this.player.draw();
}


/*
 * Starts the level : spawns enemies and ticks the game.
 * Basically the main game loop
 * @return whether the player is victorious or not
 */
public boolean start () throws UninitializedSpawner
{
	Unit.setLevel(this);
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
 * Ticks one wave, this method exists for the sole purpose of reducing indentation level of the game loop.
 * This also is a testing zone
 * @return whether the player was victorious or not
 */
public boolean startWave ()
{
	while (this.player.isAlive() && (this.spawner.isActive() || !this.enemies.isEmpty()))
	{
		long startFrameTime = System.nanoTime();

		try
		{
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

		this.tickEntities();

		StdDraw.clear();
		map.draw();
		this.drawEntities();
		this.UI.draw();
		StdDraw.show();
		}
		catch (java.util.ConcurrentModificationException eee)
		{
			// dunno how to fix concurrent accesses to entities list between display and game, they are ignored for now as they just stall display for a bit
		}


		long elapsedFrameTime = System.nanoTime() - startFrameTime;

		if (elapsedFrameTime < 16666666)  // cap to 60fps the bad way
		{
			StdDraw.pause((int)((long)16666666 - elapsedFrameTime)/1000000);
		}
	}

	return this.player.isAlive();
}
}
