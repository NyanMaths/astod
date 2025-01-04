package map;

import java.awt.geom.Point2D;


public class MultipleEnemySpawnException extends Exception
{
	public MultipleEnemySpawnException(String mapName, String levelName, Point2D.Float first, Point2D.Float second)
	{
		super("multiple enemy spawns detected : first at" + first + ", second at " + second);
	}
}
