import java.awt.Color;

public class Cell 
{
    private CellType type;

    public Cell(CellType type)
    {
        this.type = type;
    }

    public Color getColor()
    {
        switch(this.type)
        {
            case Spawn: return Color.RED;

            case Path: return new Color (194, 178, 128);

            case Player: return Color.ORANGE;

            case Buildable: return Color.LIGHT_GRAY;

            case Scenery: return new Color(11, 102, 35);

            default: throw new IllegalArgumentException("Invalid case detected");
        }
    }
}