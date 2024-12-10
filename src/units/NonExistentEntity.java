package units;

public class NonExistentEntity extends Exception
{
private final String name;

public NonExistentEntity (String name)
{
	super(name + " does not exist as a LivingEntity type !");
	this.name = name;
}

public String getName ()
{
	return name;
}
}
