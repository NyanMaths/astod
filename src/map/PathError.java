package map;


/**
 * Used to specify which king of error happened when building enemy path
 */
public enum PathError
{
	/**
	 * There is no path at all
	 */
	NoPath,
	/**
	 * There is multiple branches in enemy path
	 */
	MultiplePath,
	/**
	 * There is cycles in ~~family tree soft~~ the path
	 */
	Loop,
};
