package map;

import graphics.Coloured;
import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;

/**
 * Represents a map cell
 */
public class Cell implements Drawable, Coloured
{
/**
 * The kind of cell : builable, enemy spawn...
 */
private final CellType type;
/**
 * The position of the cell in the map's matrix
 */
private final Point2D.Float position;
/**
 * Whether or not the cell is occupied by a tower, only valid for buildable cells
 */
private boolean occupied;
/**
 * The physical size of the cells on the map
 */
private static float size;
/**
 * The next cell on the path, only applicable for spawn, path and base
 * The spawn is the first cell of a path and the last cell of the path is the base, its nextCell is null like a simply linked list
 */
private Cell nextCell;


/**
 * Constructor for Cell
 * @param type the kind of cell
 * @param position its coordinates in the map's matrix
 */
public Cell (CellType type, Point2D.Float position)
{
	this.type = type;
	this.position = position;
	this.occupied = false;
	this.nextCell = null;
}
/**
 * Same as the other constructor, but parses cellType from a character
 * @param type the kind of cell
 * @param position its coordinates in the map's matrix
 */
public Cell (Character type, Point2D.Float position)
{
	this.type = switch (type)
	{
		case 'S' -> CellType.Spawn;
		case 'R' -> CellType.Path;
		case 'B' -> CellType.Player;
		case 'C' -> CellType.Buildable;
		case 'X' -> CellType.Scenery;
		default -> throw new IllegalArgumentException(type + " is not a proper cell type");
	};

	this.position = position;
}


/**
 * Getter for cell's type
 * @return the cell's type
 */
public CellType getType ()
{
	return this.type;
}

/**
 * Getter for cells physical size
 * @return a cell physical size
 */
public static float getSize ()
{
	return size;
}
/**
 * Setter for cells physical size. Only intended to use when loading a new map.
 * @param newSize the new physical size of cells
 * @return whether or not your new size was acceptable
 */
public static boolean setSize (float newSize)
{
	if (newSize <= 0.0)
	{
		return false;
	}

	size = newSize;
	return true;
}

/**
 * Getter for cell's occupied state
 * @return whether or not the cell is occupied by a tower
 */
public boolean isOccupied()
{
	return this.occupied;
}
/**
 * Toggles the occupied state of a cell, should be called when spawning and dispawning a tower
 */
public void toggleOccupied()
{
	occupied = !occupied;
}

/**
 * Getter for the next cell in the path
 * @return the next cell on the path, null if not on path or finished
 */
public Cell getNextCell()
{
	return this.nextCell;
}
/**
 * Sets the next cell on the path, should be null for player's base and non-path cells
 * @param nextCell the next cell of the path
 */
public void setNextCell(Cell nextCell)
{
	this.nextCell = nextCell;
}

/**
 * Getter for cell's matrix position
 * @return the cell's position in map's matrix
 */
public Point2D.Float getPosition()
{
	return this.position;
}
/**
 * Getter for cell's center physical position
 * @return the cell's center point in the game
 */
public Point2D.Float getCenter ()
{
	return new Point2D.Float(size*this.position.x + 0.5f*size, size*this.position.y + 0.5f*size);
}

/**
 * Getter for cell's colour, according to its type
 * @return the cell's colour
 */
@Override
public Color getColour ()
{
	return switch (this.type)
	{
		case CellType.Spawn -> Color.RED;
		case CellType.Path -> new Color(194, 178, 128);
		case CellType.Player -> Color.ORANGE;
		case CellType.Buildable -> Color.LIGHT_GRAY;
		case CellType.Scenery -> new Color(11, 102, 35);
	};
}

/**
 * Draws the cell : a type-wise coloured square with a black border
 */
@Override
public void draw ()
{
	float x = size*this.position.x + 0.5f*size;
	float y = size*this.position.y + 0.5f*size;

	StdDraw.setPenColor(this.getColour());
	StdDraw.filledSquare(x, y, size*0.5);
	StdDraw.setPenColor(StdDraw.BLACK);
	StdDraw.square(x, y, size*0.5);
}

/**
 * Reverse parser for cell type into character
 * @return the cell's type as character
 */
@Override
public String toString ()
{
	return switch (this.type)
	{
		case CellType.Spawn -> "S";
		case CellType.Path -> "R";
		case CellType.Player -> "B";
		case CellType.Buildable -> "C";
		case CellType.Scenery -> "X";
	};
}
}
