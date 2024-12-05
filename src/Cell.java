import java.awt.Color;

public class Cell 
{
private final CellType type;

public Cell (CellType type)
{
	this.type = type;
}

public Cell (Character type)
{
	this.type = switch (type)
	{
		case 'S' -> CellType.Spawn;
		case 'R' -> CellType.Path;
		case 'B' -> CellType.Player;
		case 'C' -> CellType.Buildable;
		case 'X' -> CellType.Scenery;
		default -> throw new IllegalArgumentException("pikmin");
	};
}

public Color getColor()
{
	return switch(this.type)
	{
		case Spawn -> Color.RED;

		case Path -> new Color (194, 178, 128);

		case Player -> Color.ORANGE;

		case Buildable -> Color.LIGHT_GRAY;

		case Scenery -> new Color(11, 102, 35);

		default -> throw new IllegalArgumentException("Invalid case detected");
	};
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