package units;

import java.util.TimerTask;


/**
 * Used to schedule an enemy spawn event
 */
public class SpawnTask extends TimerTask
{
/**
 * The spawner to send the unit to
 */
private final Spawner parent;

/**
 * Constructor for SpawnTask
 * @param parent the pawner to submit the unit to
 */
public SpawnTask (Spawner parent)
{
	this.parent = parent;
}

/**
 * Initiates the spawn of a new enemy
 */
@Override
public void run ()
{
	parent.spawnNext();
}
}
