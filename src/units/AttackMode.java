package units;


/**
 * The towers target selection filter : the nearest enemy, the stringest ? pick your poison
 */
public enum AttackMode
{
	/**
	 * Opinionated default filter : the nearest without more fuss
	 */
	Nearest,
	/**
	 * Target the enemy with the most attack points
	 */
	Strongest,
	/**
	 * Target the enemy with the least attack points
	 */
	Weakest,
	/**
	 * Target the enmy with the highest max health proportion
	 */
	Healthiest,
	/**
	 * Target the enemy with the least max health proportion
	 */
	MostWounded,
	/**
	 * Target the enemy with the highest current health
	 */
	Tankiest,
	/**
	 * Target the enemy the closest to the base, TACTICAL CAMEL, INCOMING
	 */
	MostAdvanced,
}
