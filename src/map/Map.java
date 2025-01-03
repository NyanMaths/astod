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
import java.util.List;
import libraries.StdDraw;


public final class Map implements Drawable
{
private Path name;
private List<List<Cell>> matrix;
private Point2D.Float playerPosition;
private Point2D.Float spawnerPosition;
private Cell spawn = null;

public Map (String name) throws InvalidMapException, InvalidMapPathException, NoEnemySpawnException
{
	this.load(name);
}
public Map () throws InvalidMapException, InvalidMapPathException
{
	// nothing here
}

public void load (String name) throws InvalidMapException, InvalidMapPathException, NoEnemySpawnException
{
	this.name = Paths.get("assets/maps/" + name + ".mtp");

	File mapFile = new File("assets/maps/" + name + ".mtp");
	if (!mapFile.exists() || mapFile.isDirectory())
	{
		throw new InvalidMapPathException(this.name);
	}


	this.matrix = new ArrayList<>();

	try (BufferedReader reader = Files.newBufferedReader(this.name))
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
				}
				if (cellType == 'S')
				{
					spawnerCoordinates = new Point2D.Float(i, j);
					this.spawn = newCell;
				}

				this.matrix.getLast().addLast(newCell);
				j++;
			}

			currentLine = reader.readLine();
			i++;
		}

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));

		this.playerPosition = new Point2D.Float(Cell.getSize()*playerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*playerCoordinates.y + 0.5f*Cell.getSize());
		this.spawnerPosition = new Point2D.Float(Cell.getSize()*spawnerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*spawnerCoordinates.y + 0.5f*Cell.getSize());
		initializePath(null, this.getCell(this.getCellCoordinates(this.spawnerPosition)));
    }
	catch (IOException eee)
	{
		throw new InvalidMapException(this.name);
	}
}

private void initializePath(Cell previous, Cell current) throws NoEnemySpawnException, InvalidMapPathException
{
	if (current==null) throw new NoEnemySpawnException(this.name);
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

private Cell[] getAdjacentCells(Cell cell) {
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

public Point2D.Float getPlayerPosition ()
{
	return this.playerPosition;
}

public Point2D.Float getSpawnerPosition ()
{
	return this.spawnerPosition;
}
public Cell getSpawn ()
{
	return this.spawn;
}

public Path getLocation ()
{
	return this.name;
}

public int getRowsCount ()
{
	return matrix.size();
}

public int getColumnsCount ()
{
	return matrix.get(0).size();
}

public Cell getCell (int row, int col)
{
	return this.matrix.get(row).get(col);
}
public Cell getCell (Point2D.Float coordinates)
{
	return this.matrix.get((int)coordinates.x).get((int)coordinates.y);
}


@Override
public void draw ()
{
    int rowsCount = this.getRowsCount();
    int columnsCount = this.getColumnsCount();

	for (int i = 0 ; i<rowsCount ; i++)
	{
		for (int j = 0 ; j<columnsCount ; j++)
		{
			this.getCell(i, j).draw();
		}
	}
}

public void listCells()
{
	Point2D.Float spawn = this.spawnerPosition;
	Cell spawner = getCell(this.getCellCoordinates(spawn));
	while(spawner.getNextCell() != null)
	{
		System.out.println(spawner.getPosition());
		spawner = spawner.getNextCell();
	}
	System.out.println(spawner.getPosition());
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

public boolean isMapClicked ()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 350-350 && StdDraw.mouseX() < 350+350 && StdDraw.mouseY() > 350-350 && StdDraw.mouseY() < 350+350)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
}


/*
 * @return the coordinates of the point's cell, null if not on the map
 */
public Point2D.Float getCellCoordinates (Point2D.Float point)
{
	point = (Point2D.Float)point.clone();
	point.x /= Cell.getSize();
	point.y /= Cell.getSize();
	if((int)point.x >= 0 && (int)point.x < this.getRowsCount() && (int)point.y >= 0 && (int)point.y < this.getColumnsCount()) return new Point2D.Float((int)point.x, (int)point.y);
	return null;
}

public boolean isBuildable (Point2D.Float position)
{
	if (position == null) return false;

	position = this.getCellCoordinates((Point2D.Float)position.clone());

	if (position == null) return false;
	if (this.getCell((int)position.x, (int)position.y).isOccupied()) return false; //Ici, on pourra mettre le TileOccupiedException quand tu l'auras crée. J'ose pas le faire, j'ai peur de faire de la merde.

	return this.getCell((int)position.x, (int)position.y).getType() == CellType.Buildable;
}

public Point2D.Float getCenterCell(Point2D.Float cell)
{
	if(cell.x < 0 || cell.x >= this.getRowsCount() || cell.y < 0 || cell.y >= this.getColumnsCount()) return null;
	float size = Cell.getSize();
	return new Point2D.Float((cell.x*size)+size/2, (cell.y*size)+size/2);
}
}
