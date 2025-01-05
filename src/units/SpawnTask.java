package units;

import java.util.TimerTask;


public class SpawnTask extends TimerTask
{
private final Spawner parent;

public SpawnTask (Spawner parent)
{
	this.parent = parent;
}

@Override
public void run ()
{
	parent.spawnNext();
}
}
