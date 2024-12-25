import graphics.Coloured;
import graphics.Drawable;
import graphics.Utils;
import java.awt.Color;
import units.Element;
import units.living.LivingEntity;


public class Player implements Drawable, Coloured
{
private final String name;
private long maxHealth;
private long health;
private long money;
private final Element element;

public Player(String name, Element element, long maxHealth, long health, long initialMoney)
{
	this.name = name;
	this.maxHealth = maxHealth;
	this.health = health;
	this.money = initialMoney;
	this.element = element;
}


public boolean setHealth (long newHealth)
{
	if (newHealth < 0)
	{
		return false;
	}

	this.health = newHealth;
	return true;
}

public long getMoney ()
{
	return this.money;
}

public void reward (LivingEntity monster)
{
	this.money += monster.getReward();
}

@Override
public Color getColour ()
{
	return Utils.colorFromElement(this.element);
}

@Override
public void draw ()
{
	// todo : draw a heart or something dunno
}
}
