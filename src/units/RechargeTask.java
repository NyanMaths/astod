package units;

import java.util.TimerTask;


public class RechargeTask extends TimerTask
{
private final Unit cooldownedUnit;

public RechargeTask (Unit cooldownedUnit)
{
	this.cooldownedUnit = cooldownedUnit;
}

@Override
public void run ()
{
	if (this.cooldownedUnit != null)  // the unit might have died during cooldown
	{
		this.cooldownedUnit.recharge();
	}
}
}
