package graphics;

import game.Player;
import java.awt.Color;
import java.awt.geom.Point2D;
import libraries.StdDraw;
import units.towers.*;


public class LevelUI implements Drawable
{
private final Player player;

public LevelUI (Player player)
{
	this.player = player;
}


public void drawCoin()
{
	StdDraw.setPenColor (new Color(212,175,55));
	StdDraw.filledCircle (760, 641, 20);
	StdDraw.text(810, 641, ((Long)this.player.getMoney()).toString());
	StdDraw.setPenColor (new Color(192,192,192));
	StdDraw.filledCircle (760, 641, 15);

}

public void drawHeart()
{
	int centerX = 930;
	int centerY = 641;
	int halfHeight = 20;
	StdDraw . setPenColor ( new Color (223 , 75 , 95) );
	double [] listX = new double []
	{
		centerX,
		centerX-halfHeight,
		centerX-halfHeight,
		centerX-0.66*halfHeight,
		centerX-0.33*halfHeight,
		centerX,
		centerX+0.33*halfHeight,
		centerX+0.66*halfHeight,
		centerX+halfHeight,
		centerX+halfHeight,
	};
	double [] listY = new double []
	{
		centerY-halfHeight,
		centerY,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY+halfHeight,
		centerY+halfHeight,
		centerY+0.5*halfHeight,
		centerY,
		};
		StdDraw.filledPolygon(listX , listY);
		StdDraw.text(980,641, ((Long)this.player.getHealth()).toString());
}

public void drawLevelInfo() //il va prendre le niveau et la vague actuelle
{
	StdDraw.text(760,688,"LVL:X/X");
	StdDraw.text(930,688,"WAVE:X/X");
	//J'aimerais test de changer le font, mais je sais pas si c'est une très bonne idée, car faudra le changer entre les fonctions. 
	//Genre pour la vie et la thune, c'est un style différent de celui pour la vague et le niveau en terme d'écriture.
}

public void drawShop()
{

	float x1 = 795;
	float y1 = 545;
	float halfWidth = 72;
	float halfHeight = (float) 60.5;
	float x2 = x1+2*halfWidth;
	float y2 = y1-2*halfHeight;
	float y3 = y1-4*halfHeight;
	StdDraw.rectangle(x1, y1, halfWidth, halfHeight);
	Point2D.Float spawnPosition = new Point2D.Float(x1-55,y1);
	Archer archer = new Archer(spawnPosition);
	archer.draw();
	StdDraw.rectangle(x2, y1, halfWidth, halfHeight);
	spawnPosition.setLocation(x2-55,y1);
	EarthCaster earth = new EarthCaster(spawnPosition);
	earth.draw();
	StdDraw.rectangle(x1,y2,halfWidth,halfHeight);
	spawnPosition.setLocation(x1-55,y2);
	FireCaster fire = new FireCaster(spawnPosition);
	fire.draw();
	StdDraw.rectangle(x2,y2,halfWidth,halfHeight);
	spawnPosition.setLocation(x2-55,y2);
	WaterCaster water = new WaterCaster(spawnPosition);
	water.draw();
	StdDraw.rectangle(x1,y3,halfWidth,halfHeight);
	spawnPosition.setLocation(x1-55,y3);
	WindCaster wind = new WindCaster(spawnPosition);
	wind.draw();
}

@Override
public void draw ()
{
	StdDraw.setPenColor(Color.BLACK);
	StdDraw.rectangle(867,688,144,12);
	StdDraw.rectangle(867,641,144,25);
	StdDraw.rectangle(867, 303, 144,303);
	this.drawLevelInfo();
	this.drawShop();
	this.drawCoin();
	this.drawHeart();
}
}
