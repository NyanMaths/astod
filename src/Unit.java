/*
Unit : an abstract class to define basics of towers and living entities.
The default attack mode is intended to be defined by the player directly in order not to bother setting it manually each time they invoke an ally.
The attacker property is a flag to identify a unit as ally or enemy.
*/
public abstract class Unit
{
private static AttackMode defaultAttackMode;

private String name;
private Element element;
private boolean attacker;

private long maxHealth;
private long health;
private long attack;
private long range;

private AttackMode attackMode;


public Unit (String name, boolean attacker, Element element, long maxHealth, long attack, long range, long health)
{
    this.name = name;
    this.attacker = attacker;
    this.element = element;

    this.maxHealth = maxHealth;
    this.health = health;
    this.attack = attack;
    this.range = range;

    this.attackMode = defaultAttackMode;
}
public Unit (String name, boolean attacker, Element element, long maxHealth, long attack, long range)
{
    this(name, attacker, element, maxHealth, attack, range, maxHealth);
}


/**
 * 
 * @param what kind of elemental is applied to the Unit
 * @return the multiplier to be applied to the damage taken
 */
private float getDamageMultiplier (Element damageElement)
{
    if ((this.element == Element.Fire && damageElement == Element.Water) ||
        (this.element == Element.Water && damageElement == Element.Wind) ||
        (this.element == Element.Wind && damageElement == Element.Earth) ||
        (this.element == Element.Earth && damageElement == Element.Fire))
    {
        return 1.5f;
    }

    return 1.0f;
}


public String getName ()
{
    return this.name;
}

public Element getElement ()
{
    return this.element;
}

public boolean isAttacker ()
{
    return this.attacker;
}
public void setAttacker (boolean newStatus)  // May require special magician token to allow operation as it's quite uncommon to switch teams as easily as Ciotti
{
    this.attacker = newStatus;
}

public long getMaxHealth ()
{
    return this.maxHealth;
}
/** Setter for Unit's max health
 * @return whether the new value is acceptable (> 1) or not
*/
public boolean setMaxHealth (long newMaxHealth)  // May also require special magician token to allow operation
{
    if (newMaxHealth < 1)
    {
        return false;
    }

    this.maxHealth = newMaxHealth;
    return true;
}

public long getHealth ()
{
    return this.health;
}
/** Setter for Unit's health
 * @return whether the unit is still alive or not
*/
private boolean setHealth (long newHealth)
{
    if (newHealth < 1)
    {
        this.health = 0;
        return false;
    }
    else if (newHealth > this.maxHealth)
    {
        this.health = this.maxHealth;
        return true;
    }

    this.maxHealth = newHealth;
    return true;
}
/** Hurts or heals the Unit
 * @return the damage actually taken by the Unit (negative if healed)
*/
public long hurt (long damage, Element damageElement)
{
    long damageToInflict;

    if (damage >= 0)
    {
        damageToInflict = (long)((float)damage * getDamageMultiplier(damageElement));
    }
    else
    {
        damageToInflict = damage;
    }


    if (damageToInflict < this.maxHealth && damage >= 0)  // Normal damage case
    {
        this.setHealth(this.maxHealth - damageToInflict);
        return damageToInflict;
    }
    else if (damageToInflict < this.maxHealth)  // Healing case
    {
        long uncappedHealth = this.health + damageToInflict;

        if (uncappedHealth > this.maxHealth)
        {
            this.setHealth(this.maxHealth);
            return uncappedHealth - this.maxHealth;
        }

        this.setHealth(uncappedHealth);
        return damageToInflict;
    }


    // Dies from death

    long currentHealth = this.health;
    this.setHealth(0);
    return currentHealth;
}

public long getAttack ()
{
    return this.attack;
}
// Setter too dangerous for now

public long getRange ()
{
    return this.range;
}
// Setter useless for now

public AttackMode getaAttackMode ()
{
    return this.attackMode;
}
public void setAttackMode (AttackMode newAttackmode)
{
    this.attackMode = newAttackmode;
}
}
