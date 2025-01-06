package map;

import graphics.Drawable;
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
import libraries.StdDraw;


/**
 * Represents the map : stores all cells and their metadata, but not the entities
 */
public final class Map implements Drawable
{
/**
 * The identifier of the map
 */
private String name;
/**
 * The name of the level
 */
private String levelName;
/**
 * The location of the map
 */
private Path location;
/**
 * The cells matrix
 */
private List<List<Cell>> matrix;
/**
 * The player's position
 */
private Point2D.Float playerPosition;
/**
 * The spawner's position
 */
private Point2D.Float spawnerPosition;
/**
 * The player's bases, should be only one
 */
private List<Cell> playerBases;
/**
 * The enemy spawn cells, sould be only one
 */
private List<Cell> spawns;


/**
 * Constructor for map, loads the matrix and computes all useful metadata like spawn and player's position or the enemy path in the map to access it later with O(n) complexity
 * @param name the identifier of the map, see assets/maps to add more
 * @param levelName the name of the associated level
 * @throws InvalidMapException if the map is funky
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is multiple enemy spawns
 * @throws NoPlayerBaseException if there is no base for the player
 * @throws MultiplePlayerBaseException if there is too much bases for player
 * @throws NonExistentMapException if the map's path is unreadable (directory or non-existent)
 */
public Map (String name, String levelName) throws InvalidMapException, NonExistentMapException, NoEnemySpawnException, MultipleEnemySpawnException, MultiplePlayerBaseException, NoPlayerBaseException
{
	this.load(name, levelName);
}
/**
 * Default constructor for map, requires to use Map.load later
 */
public Map ()
{
	// nothing here
}

/**
 * Loads the map's matrix and computes all useful metadata like spawn and player's position or the enemy path in the map to access it later with O(n) complexity
 * @param name the identifier of the map, see assets/maps to add more
 * @param levelName the name of the associated level
 * @throws InvalidMapException if the map is funky
 * @throws NoEnemySpawnException if there is no enemy spawn
 * @throws MultipleEnemySpawnException if there is multiple enemy spawns
 * @throws NoPlayerBaseException if there is no base for the player
 * @throws MultiplePlayerBaseException if there is too much bases for player
 * @throws NonExistentMapException if the map's path is unreadable (directory or non-existent)
 */
public void load (String name, String levelName) throws InvalidMapException, NonExistentMapException, NoEnemySpawnException, MultipleEnemySpawnException, MultiplePlayerBaseException, NoPlayerBaseException
{
	this.name = name;
	this.levelName = levelName;
	this.location = Paths.get("assets/maps/" + name + ".mtp");
	this.playerBases = new LinkedList<>();
	this.spawns = new LinkedList<>();

	File mapFile = new File("assets/maps/" + name + ".mtp");
	if (!mapFile.exists() || mapFile.isDirectory())
	{
		throw new NonExistentMapException(this.location);
	}


	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.location))
	{
		String currentLine = reader.readLine();

		Point2D.Float playerCoordinates = new Point2D.Float(0.0f, 0.0f);
		Point2D.Float spawnerCoordinates = new Point2D.Float(0.0f, 0.0f);

		int i = 0;
		while (currentLine != null)
		{
			this.matrix.addLast(new ArrayList<>());
			int j = 0;
			for (Character cellType : currentLine.toCharArray())
			{
				Cell newCell = new Cell(cellType, new Point2D.Float(i, j));

				if (cellType == 'B')
				{
					playerCoordinates = new Point2D.Float(i, j);
					this.playerBases.addLast(newCell);
				}
				if (cellType == 'S')
				{
					spawnerCoordinates = new Point2D.Float(i, j);
					this.spawns.addLast(newCell);
				}

				this.matrix.getLast().addLast(newCell);
				j++;
			}

			currentLine = reader.readLine();
			i++;
		}

		if (this.spawns.isEmpty()) throw new NoEnemySpawnException(this.name, this.levelName);
		else if (this.spawns.size() > 1) throw new MultipleEnemySpawnException(name, levelName, this.spawns);

		if (this.playerBases.isEmpty()) throw new NoPlayerBaseException(this.name, this.levelName);
		else if (this.playerBases.size() > 1) throw new MultiplePlayerBaseException(name, levelName, this.playerBases);

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));

		this.playerPosition = new Point2D.Float(Cell.getSize()*playerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*playerCoordinates.y + 0.5f*Cell.getSize());
		this.spawnerPosition = new Point2D.Float(Cell.getSize()*spawnerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*spawnerCoordinates.y + 0.5f*Cell.getSize());

		initializePath(null, this.spawns.getFirst());
    }
	catch (IOException eee)
	{
		throw new InvalidMapException(this.location);
	}
}

/**
 * Recursive path constructor for map
 * @param previous the previous cell to avoid setting next cell to it, begin with null
 * @param current the current cell which needs their nextCell to be set, begin with enemy spawn
 */
private void initializePath (Cell previous, Cell current)
{
	if (current.getType()==CellType.Player) return;
	Cell[] adjacentCells = getAdjacentCells(current);


    for (Cell next : adjacentCells)
	{
        if (next != null && next.getType() == CellType.Path && next != previous)
		{
            current.setNextCell(next);
            initializePath(current, next);
            break;
		}
		if (next != null && next.getType() == CellType.Player && next != previous)
		{
            current.setNextCell(next);
            initializePath(current, next);
            break;
		}
	}
	//throw new InvalidMapPathException(this.name);
}
/**
 * Get top, bottom, left and right cells from one cell
 * @param cell the cell in need of friends
 * @return cell's closest friends
 */
private Cell[] getAdjacentCells (Cell cell)
{
	Point2D.Float cellPosition = cell.getPosition();
	Point2D.Float up = new Point2D.Float(cellPosition.x, (cellPosition.y - 1));
	Point2D.Float down = new Point2D.Float(cellPosition.x, (cellPosition.y + 1));
	Point2D.Float left = new Point2D.Float((cellPosition.x - 1), cellPosition.y);
	Point2D.Float right = new Point2D.Float((cellPosition.x + 1), cellPosition.y);

    Cell cellUp = getCell(up);
    Cell cellDown = getCell(down);
    Cell cellLeft = getCell(left);
    Cell cellRight = getCell(right);

    return new Cell[]{cellUp, cellDown, cellLeft, cellRight};
}

/**
 * Getter for player's position on map
 * @return the player's position on map
 */
public Point2D.Float getPlayerPosition ()
{
	return this.playerPosition;
}

/**
 * Getter for enemy spawn position on map
 * @return the spawner's position
 */
public Point2D.Float getSpawnerPosition ()
{
	return this.spawnerPosition;
}
/**
 * Getter for spawn cell, supposes unique spawn
 * @return the spawn cell
 */
public Cell getSpawn ()
{
	return this.spawns.getFirst();
}

/**
 * Getter for map's location
 * @return the map's location
 */
public Path getLocation ()
{
	return this.location;
}

/**
 * Getter for map's matrix height
 * @return the matrix height
 */
public int getRowsCount ()
{
	return matrix.size();
}

/**
 * Getter for map's matrix length
 * @return the matrix length
 */
public int getColumnsCount ()
{
	return matrix.get(0).size();
}

/**
 * Getter for matrix[row, col]
 * @param row the row
 * @param col the ~~row~~ column
 * @return the cell at (row, col)
 */
public Cell getCell (int row, int col)
{
	return this.matrix.get(row).get(col);
}
/**
 * Getter for matrix[coordinates.x, coordinates.y]
 * @param coordinates the point to get the cell from
 * @return the cell at (coordinates.x, coordinates.y)
 */
public Cell getCell (Point2D.Float coordinates)
{
	return this.matrix.get((int)coordinates.x).get((int)coordinates.y);
}


/**
 * Draws the map
 */
@Override
public void draw ()
{
	this.matrix.stream().forEachOrdered(row->row.stream().forEachOrdered(cell->cell.draw()));
}


/* public boolean isShopClicked()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 876-144 && StdDraw.mouseX() < 867+144 && StdDraw.mouseY() > 303-303 && StdDraw.mouseY() < 303+303)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
} */

/**
 * Know if the map was clicked
 * @return whether or not the player clicked on the map
 */
public boolean isMapClicked ()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 350-350 && StdDraw.mouseX() < 350+350 && StdDraw.mouseY() > 350-350 && StdDraw.mouseY() < 350+350)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
}


/**
 * Allows to know the coordinates in the map's matrix of a point on the screen
 * @param point the physical position to extract the cell from
 * @return the coordinates of the point's cell, null if not on the map
 */
public Point2D.Float getCellCoordinates (Point2D.Float point)
{
	point = (Point2D.Float)point.clone();
	point.x /= Cell.getSize();
	point.y /= Cell.getSize();
	if ((int)point.x >= 0 && (int)point.x < this.getRowsCount() && (int)point.y >= 0 && (int)point.y < this.getColumnsCount()) return new Point2D.Float((int)point.x, (int)point.y);
	return null;
}

/**
 * Know if a physical position on the map is buildable
 * @param position the position to examine
 * @return whether or not the cell at position is buildable
 */
public boolean isBuildable (Point2D.Float position)
{
	if (position == null) return false;

	position = this.getCellCoordinates((Point2D.Float)position.clone());

	if (position == null) return false;
	if (this.getCell((int)position.x, (int)position.y).isOccupied()) return false; //Ici, on pourra mettre le TileOccupiedException quand tu l'auras crée. J'ose pas le faire, j'ai peur de faire de la merde.

	return this.getCell((int)position.x, (int)position.y).getType() == CellType.Buildable;
}
}
