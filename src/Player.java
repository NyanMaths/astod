public class Player 
{
    private double health;
    private double money;

    public Player(double health, double money)
    {
        this.health = health;
        this.money = money;
    }

    public double getHealth() 
    {
        return this.health;
    }

    public double getMoney()
    {
        return this.money;
    }
}
