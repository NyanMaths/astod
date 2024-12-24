import units.living.LivingEntity;


public class Player
{
private long health;
private long money;

public Player(long health, long money)
{
	this.health = health;
	this.money = money;
}

public long getHealth () 
{
	return this.health;
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
}
