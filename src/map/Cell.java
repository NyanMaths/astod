package map;

import graphics.Coloured;
import graphics.Drawable;
import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;

public class Cell implements Drawable, Coloured
{
private final CellType type;
private final Point2D.Float position;
private boolean occupied;
private static float size;

public Cell (CellType type, Point2D.Float position, boolean occupied)
{
	this.type = type;
	this.position = position;
	this.occupied = false;
}
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


public CellType getType ()
{
	return this.type;
}

public static float getSize ()
{
	return size;
}
public static boolean setSize (float newSize)
{
	if (newSize <= 0.0)
	{
		return false;
	}

	size = newSize;
	return true;
}

public boolean isOccupied()
{
	return this.occupied;
}

public void setOccupied()
{
	occupied = !occupied; //pas certain a 100% de ce que je fais, mais : Just works	
}

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