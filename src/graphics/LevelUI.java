package graphics;

import game.Level;
import game.Player;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import libraries.StdDraw;
import units.towers.*;


/**
 * This class groups the shop and the level's information together
 */
public class LevelUI implements Drawable
{
/**
 * The level's player to get information from
 */
private final Player player;
/**
 * The level to communicate with
 */
private final Level level;
/**
 * If the user clicked on a tower, this stands as the potential spawn
 */
private Tower spawningTower;

/**
 * Every tower's spawn buttons in the shop
 */
private final List<SpawnButton> spawnButtons;


/**
 * Constructor for the level's UI
 * @param player the player to get information from
 * @param level the level to communicate with
 */
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


/**
 * Allows to know whether the user wants to buy a tower
 * @return if the user is buying a tower
 */
public boolean isBuildingTower ()
{
	return this.spawningTower != null;
}
/**
 * Allows to know if the shop is clicked, allows smaller comlexities
 * @return whether the whop is being pressed or not
 */
public boolean isShopPressed ()
{
	return StdDraw.mouseX() >= 795 - SpawnButton.getHalfWidth() && StdDraw.mouseY() <= 545 + SpawnButton.getHalfHeight();
}

/**
 * Getter for player's shopping item, consumes the item
 * @return the tower the player wants to buy
 */
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
/**
 * Changes the incoming purchase, supposes non-goofy argument
 * @param towerName the new tower to buy
 */
public void setSpawningTower (String towerName)
{
	this.spawningTower = Tower.fromName(towerName, new Point2D.Float(0, 0));
}

/**
 * Get the selected tower type to buy
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


/**
 * Draws a coin at the right place, wrongly placed code, to be moved to graphics.Utils
 */
public void drawCoin()
{
	StdDraw.setPenColor (new Color(212,175,55));
	StdDraw.filledCircle (760, 641, 20);
	StdDraw.text(810, 641, ((Long)this.player.getMoney()).toString());
	StdDraw.setPenColor (new Color(192,192,192));
	StdDraw.filledCircle (760, 641, 15);

}

/**
 * Draws a heart at the right place, duplicated code to be yeeten
 */
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

/**
 * Draws level informations at the top : level name and current wave
 */
public void drawLevelInfo ()
{
	StdDraw.text(780, 688, "Level : " + this.level.getName());
	StdDraw.text(930, 688, "Wave : " + this.level.getCurrentWaveName());
}

/**
 * Draws all shops's buttons to buy towers
 */
public void drawShop ()
{
	spawnButtons.stream().forEach(spawnButton -> spawnButton.draw());
}

/**
 * Draws the entire UI
 */
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
