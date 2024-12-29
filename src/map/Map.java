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

public Map (String name) throws InvalidMapException, InvalidMapPathException
{
	this.load(name);
}
public Map () throws InvalidMapException, InvalidMapPathException
{
	// nothing here
}

public void load (String name) throws InvalidMapException, InvalidMapPathException
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
				if (cellType == 'B')
				{
					playerCoordinates = new Point2D.Float(i, j);
				}
				if (cellType == 'S')
				{
					spawnerCoordinates = new Point2D.Float(i, j);
				}

				this.matrix.getLast().addLast(new Cell(cellType, new Point2D.Float(i, j)));
				j++;
			}
			
			currentLine = reader.readLine();
			i++;
		}

		Cell.setSize(Math.min(1024.0f, 720.0f) / (float)Math.max(this.getRowsCount(), this.getColumnsCount()));

		this.playerPosition = new Point2D.Float(Cell.getSize()*playerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*playerCoordinates.y + 0.5f*Cell.getSize());
		this.spawnerPosition = new Point2D.Float(Cell.getSize()*spawnerCoordinates.x + 0.5f*Cell.getSize(), Cell.getSize()*spawnerCoordinates.y + 0.5f*Cell.getSize());
    }
	catch (IOException eee)
	{
		throw new InvalidMapException(this.name);
	}
}


public Point2D.Float getPlayerPosition ()
{
	return this.playerPosition;
}

public Point2D.Float getSpawnerPosition ()
{
	return this.spawnerPosition;
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

/* public boolean isShopClicked()
{
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > 876-144 && StdDraw.mouseX() < 867+144 && StdDraw.mouseY() > 303-303 && StdDraw.mouseY() < 303+303)
	{
		StdDraw.pause(100);
		return true;
	}
	return false;
} */

public String whichTower()
{
	float x1 = 795;
	float y1 = 545;
	float halfWidth = 72;
	float halfHeight = (float) 60.5;
	float x2 = x1+2*halfWidth;
	float y2 = y1-2*halfHeight;
	float y3 = y1-4*halfHeight;
	halfWidth = (float) 20.5;
	halfHeight = (float) 10.5;
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > (x1+25)-halfWidth && StdDraw.mouseX() < (x1+25)+halfWidth && StdDraw.mouseY() > (y1-48)-halfHeight && StdDraw.mouseY() < (y1-48)+halfHeight)
	{
		StdDraw.pause(100);
		return "Archer";
	}
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > (x2+25)-halfWidth && StdDraw.mouseX() < (x2+25)+halfWidth && StdDraw.mouseY() > (y1-48)-halfHeight && StdDraw.mouseY() < (y1-48)+halfHeight)
	{
		StdDraw.pause(100);
		return "Earth Caster";
	}
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > (x1+25)-halfWidth && StdDraw.mouseX() < (x1+25)+halfWidth && StdDraw.mouseY() > (y2-48)-halfHeight && StdDraw.mouseY() < (y2-48)+halfHeight)
	{
		StdDraw.pause(100);
		return "Fire Caster";
	}
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > (x2+25)-halfWidth && StdDraw.mouseX() < (x2+25)+halfWidth && StdDraw.mouseY() > (y2-48)-halfHeight && StdDraw.mouseY() < (y2-48)+halfHeight)
	{
		StdDraw.pause(100);
		return "Water Caster";
	}
	if(StdDraw.isMousePressed() && StdDraw.mouseX() > (x1+25)-halfWidth && StdDraw.mouseX() < (x1+25)+halfWidth && StdDraw.mouseY() > (y3-48)-halfHeight && StdDraw.mouseY() < (y3-48)+halfHeight)
	{
		StdDraw.pause(100);
		return "Wind Caster";
	}
	return null;
}

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
 * @return the coordinates of the point's cell, null i not on the map
 */
public Point2D.Float getCellCoordinates (Point2D.Float point)
{
	point.x = point.x/Cell.getSize();
	point.y = point.y/Cell.getSize();
	if((int)point.x >= 0 && (int)point.x < this.getRowsCount() && (int)point.y >= 0 && (int)point.y < this.getColumnsCount()) return new Point2D.Float((int)point.x, (int)point.y);
	return null;
}

public boolean isBuildable(Point2D.Float position)
{
	if (position == null) return false;

	position = this.getCellCoordinates(position);

	if (position == null) return false;


	return this.getCell((int)position.x, (int)position.y).getType() == CellType.Buildable;
}
}
