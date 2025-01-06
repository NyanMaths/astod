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
import map.Map;
import map.MultipleEnemySpawnException;
import map.NoEnemySpawnException;
import map.NonExistentMapException;
import units.AttackMode;
import units.Element;
import units.Spawner;
import units.UninitializedSpawner;
import units.Unit;
import units.living.LivingEntity;
import units.towers.Tower;


/**
 * A level consists in a map, waves and a game loop to get through for the player
 */
public final class Level
{
/**
 * The level's waves
 */
private Queue<String> waves;
/**
 * The level's map
 */
private final Map map;
/**
 * The level's spawner
 */
private final Spawner spawner;
/**
 * The level's enemies list, filled by the spawner
 */
private final List<Unit> enemies;
/**
 * The player's towers
 */
private final List<Unit> towers;

/**
 * The player to protect
 */
private final Player player;

/**
 * The level's user interface, including shop and status
 */
private final LevelUI UI;
/**
 * The level's identifier
 */
private final String name;
/**
 * The pushed wave
 */
private String currentWaveName;


/**
 * Constructor of level
 * @param levelName the identifier of the level, see in assets/levels to add more
 * @throws InvalidMapException if the map's loading fails
 * @throws InvalidLevelException if the level file is corrupted
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is multiple enemy spawns
 * @throws NonExistentMapException if the map's path is unreadable
 */
public Level (String levelName) throws InvalidMapException, InvalidLevelException, NoEnemySpawnException, MultipleEnemySpawnException, NonExistentMapException
{
	this.map = new Map();
	this.spawner = new Spawner(this);
	this.enemies = new ArrayList<>();
	this.towers = new ArrayList<>();
	this.player = new Player(System.getProperty("user.name", "Player"), Element.Neutral, 100, 500, 100);
	this.UI = new LevelUI(this.player, this);
	this.name = levelName;
	this.currentWaveName = "pikmin";

	this.load(levelName);
}

/**
 * Effective constructor of level, loads the map, the waves and checks for errors in resource files
 * @param levelName the identifier of the level, see in assets/levels to add more
 * @throws InvalidMapException if the map's loading fails
 * @throws InvalidLevelException if the level file is corrupted
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is multiple enemy spawns
 * @throws NonExistentMapException if the map's path is unreadable
 */
public void load (String levelName) throws InvalidMapException, InvalidLevelException, NoEnemySpawnException, MultipleEnemySpawnException, NonExistentMapException
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
		this.map.load(currentLine, levelName);
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

/**
 * Getter of level's name
 * @return the level's name or identifier
 */
public String getName ()
{
	return this.name;
}
/**
 * Getter for currently pushed wave
 * @return the currently pushed wave
 */
public String getCurrentWaveName ()
{
	return this.currentWaveName;
}


/**
 * Allows to get all surrounding enemies of a unit, has O(n) complexity
 * @param unit the origin of the research
 * @param maxDistance the radius to look for enemies
 * @return all enemies within maxDistance from the origin
 */
private List<Unit> getNearbyEnemies (Unit unit, float maxDistance)
{
	List<Unit> nearbyEnemies = unit.isAttacker() ? this.towers : this.enemies;
	if (maxDistance >= 0.001)
	{
		nearbyEnemies = nearbyEnemies.stream().filter(enemy->unit.distance(enemy) <= maxDistance).collect(Collectors.toList());
	}

	return nearbyEnemies;
}

/**
 * Allows to get all surrounding allies of a unit, has O(n) complexity
 * @param unit the origin of the research
 * @param maxDistance the radius to look for allies
 * @return all allies within maxDistance from the origin
 */
public List<Unit> getNearbyAllies (Unit unit, float maxDistance)
{
	return (maxDistance >= 0.001 ? (unit.isAttacker() ? this.enemies : this.towers).stream().filter(ally->unit.distance(ally) <= maxDistance).collect(Collectors.toList()) : (unit.isAttacker() ? this.enemies : this.towers));
	// remember kids : don't write unreadable code unless you want to never be replaced because no one would want to maintain your hideous thingy
}

/**
 * Look for unit's nearest enemy respecting the given filter, has O(n) complexity
 * @param unit the origin of the research
 * @param maxDistance the radius to look enemies for
 * @param filter the search criteria (tankiest, weakest, just nearest...)
 * @return the nearest enemy respecting filter
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
				long currentEnemyHealthRatio = currentEnemy.getHealth() / currentEnemy.getMaxHealth();
				long currentTargetHealthRatio = currentTarget.getHealth() / currentTarget.getMaxHealth();

				if (currentEnemyHealthRatio > currentTargetHealthRatio)
				{
					currentTarget = currentEnemy;
				}
				else if (currentEnemyHealthRatio == currentTargetHealthRatio && unit.distance(currentTarget) > unit.distance(currentEnemy))
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
			case AttackMode.Tankiest ->
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
			case AttackMode.MostAdvanced ->
			{
				if (currentEnemy.getAdvancement() > currentTarget.getAdvancement())
				{
					currentTarget = currentEnemy;
				}
			}
		}
		}
	}

	return currentTarget;
}


/**
 * Adds the newly spawned enemy into battle
 * @param enemy the spawned unit
 */
public void spawn (LivingEntity enemy)
{
	this.enemies.add(enemy);
}
/**
 * Adds the newly spawned tower on the map if possible
 * @param position the spawning position of the new tower
 */
public void buildTowerFromUISelection (Point2D.Float position)
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
/**
 * Yeet a unit from the game and make it woe (LOUD)
 * @param unit the unit to blight
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

	if (!unit.getClass().getName().equals("units.living.Boss"))
	{
		return;
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
/**
 * Hurt the player and dispawn the lucky enemy
 * @param enemy the victorious enemy
 */
public void slapPlayer (LivingEntity enemy)
{
	this.player.hurt(enemy.getAttack());
	this.enemies.remove(enemy);

	if (this.player.isAlive())
	{
		return;
	}

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


/**
 * Make entities move, attack, maybe even die ; in short, make their lives.
 */
private void tickEntities ()
{
	this.enemies.stream().filter(enemy->enemy != null).forEach(enemy->enemy.tick());
	this.towers.stream().filter(tower->tower != null).forEach(tower->tower.tick());
}


/**
 * Draw enemies, towers then player from bottom to top layer.
 * Can raise ConcurrentModificationException for funny reasons.
 */
private void drawEntities ()
{
	this.enemies.stream().forEach(enemy->enemy.draw());
	this.towers.stream().forEach(tower->tower.draw());
	this.player.draw();
}


/**
 * The level's loop, pushes all enemies waves and waits
 * @return whether the player is victorious or not
 * @throws UninitializedSpawner if the spawner could not load its waves
 */
public boolean start () throws UninitializedSpawner
{
	Unit.setLevel(this);
	String wave = this.waves.poll();

	while (wave != null)
	{
		this.currentWaveName = wave;
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

/**
 * Ticks one wave, this method exists for the sole purpose of reducing indentation level of the game loop.
 * First manages mouse events, then ticks entities and lastly draws the result. Uses timers to cap frame rate, this is awful but just works :tm:
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
					buildTowerFromUISelection(position);
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

		if (elapsedFrameTime < 14492754)  // cap to 69fps the bad way
		{
			StdDraw.pause((int)((long)14492754 - elapsedFrameTime)/1000000);
		}
	}

	return this.player.isAlive();
}
}
