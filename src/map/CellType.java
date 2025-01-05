package map;


/**
 * The type of a cell used to identify buildable, spawn...
 */
public enum CellType
{
	/**
	 * Spawnpoint cell for enemies
	 */
	Spawn,
	/**
	 * Path cell for nemies to stride to the player's base
	 */
	Path,
	/**
	 * The player's base
	 */
	Player,
	/**
	 * Constructible cell for player's towers, can only hold 1 tower at a time
	 */
	Buildable,
	/**
	 * Pretty cell uwu
	 */
	Scenery,
}
