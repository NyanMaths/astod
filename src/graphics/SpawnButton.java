package graphics;

import java.awt.geom.Point2D;

import libraries.StdDraw;
import units.towers.Tower;
import java.awt.Color;



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

public Tower spawn ()
{
	return Tower.fromName(this.towerName,new Point2D.Float((float)StdDraw.mouseX(), (float)StdDraw.mouseY()));
}

@Override
public void draw ()
{
	double x = this.position.x;
	double y = this.position.y;
	StdDraw.setPenColor(Color.GRAY);
	StdDraw.filledRectangle(x, y, 20, 10);
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.rectangle(x, y, 20.5, 10.5);
	StdDraw.setPenColor(Color.WHITE);
	StdDraw.text(x, y, "BUY");
	StdDraw.setPenColor(Color.BLACK);
}
}