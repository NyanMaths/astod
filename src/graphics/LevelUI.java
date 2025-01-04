package graphics;

import game.Level;
import game.Player;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import libraries.StdDraw;
import units.towers.*;


public class LevelUI implements Drawable
{
private final Player player;
private final Level level;
private Tower spawningTower;

private final List<SpawnButton> spawnButtons;


public LevelUI (Player player, Level level)
{
	this.player = player;
	this.level = level;
	this.spawningTower = null;

	this.spawnButtons = new ArrayList<>();

	float buttonWidth = 144.0f;
	float buttonHeight = 121.0f;

	spawnButtons.add(new SpawnButton("Archer", new Point2D.Float(795, 545)));
	spawnButtons.add(new SpawnButton("Earth Caster", new Point2D.Float(795, 545 - buttonHeight)));
	spawnButtons.add(new SpawnButton("Fire Caster", new Point2D.Float(795 + buttonWidth, 545 - buttonHeight)));
	spawnButtons.add(new SpawnButton("Water Caster", new Point2D.Float(795, 545 - 2*buttonHeight)));
	spawnButtons.add(new SpawnButton("Wind Caster", new Point2D.Float(795 + buttonWidth, 545 - 2*buttonHeight)));
	spawnButtons.add(new SpawnButton("Ice Caster", new Point2D.Float(795, 545 - 3*buttonHeight)));
}


public boolean isBuildingTower ()
{
	return this.spawningTower != null;
}
public boolean isShopPressed ()
{
	return StdDraw.mouseX() >= 795 - SpawnButton.getHalfWidth() && StdDraw.mouseY() <= 545 + SpawnButton.getHalfHeight();
}

public String takeTowerName ()
{
	if (this.spawningTower == null)
	{
		return null;
	}

	String towerName = this.spawningTower.getName();
	this.spawningTower = null;

	return towerName;
}
public void setSpawningTower (String towerName)  // supposes non-goofy argument
{
	this.spawningTower = Tower.fromName(towerName, new Point2D.Float(0, 0));
}

/*
 * @return the pressed tower's type, null if no tower was selected (the shop is not full)
 */
public String getPressedTowerType ()
{
	for (SpawnButton spawnButton : this.spawnButtons)
	{
		if (spawnButton.isPressed())
		{
			return spawnButton.getTowerName();
		}
	}

	return null;
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

public void drawLevelInfo ()
{
	StdDraw.text(780, 688, "Level : " + this.level.getName());
	StdDraw.text(930, 688, "Wave : " + this.level.getCurrentWaveName());
}

public void drawShop ()
{
	spawnButtons.stream().forEach(spawnButton -> spawnButton.draw());
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
