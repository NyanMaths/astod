package graphics;

import java.awt.geom.Point2D;
import units.towers.Tower;



public class SpawnButton implements Drawable
{
private final String towerName;
private final Point2D.Float position;

public SpawnButton (String towerName, Point2D.Float position)
{
	this.towerName = towerName;
	this.position = position;
}

public String getTowerName ()
{
	return this.towerName;
}

public Tower spawn ()  // todo : use cursor's position
{
	return Tower.fromName(this.towerName, this.position);
}

@Override
public void draw ()
{

}
}