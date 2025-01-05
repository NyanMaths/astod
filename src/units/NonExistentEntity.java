package units;


/**
 * Thrown when someone mistypes the entity name
 */
public class NonExistentEntity extends Exception
{
/**
 * The unknown entity name
 */
private final String name;

/**
 * Constructor for NonExistentEntity
 * @param name the unknown entity
 */
public NonExistentEntity (String name)
{
	super(name + " does not exist as a LivingEntity type !");
	this.name = name;
}

/**
 * Getter for entity's name
 * @return the unknown entity name
 */
public String getName ()
{
	return name;
}
}
