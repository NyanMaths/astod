package units;

import java.util.TimerTask;


/**
 * Used to uncooldown a unit when recently fired
 */
public class RechargeTask extends TimerTask
{
/**
 * The unit to uncooldown
 */
private final Unit cooldownedUnit;

/**
 * Constructor for the recharge task
 * @param cooldownedUnit the unit to uncooldown
 */
public RechargeTask (Unit cooldownedUnit)
{
	this.cooldownedUnit = cooldownedUnit;
}

/**
 * Uncooldowns the unit once called by the scheduler
 */
@Override
public void run ()
{
	if (this.cooldownedUnit != null)  // the unit might have died during cooldown
	{
		this.cooldownedUnit.recharge();
	}
}
}
