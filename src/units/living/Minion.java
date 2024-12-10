package units.living;

import java.awt.geom.Point2D;
import units.Element;

/*
The most basic enemy : the minion
Weak yet reliable, this is the best companion of a fearsome villain.
*/

public final class Minion extends LivingEntity
{
public Minion (Point2D.Float spawnPosition) 
{
	super("Minion", true, Element.Neutral, 10, 3, 0, 1000, spawnPosition, 1);
}
}
