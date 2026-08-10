// Shared base for anything with hit points.

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class LivingEntity extends Entity {
    private int health;
    private final int maxHealth;
    private final List<StatusEffect> effects = new ArrayList<>();

    public LivingEntity(int x, int y, int maxHealth) {
        super(x, y);
        this.maxHealth = maxHealth;
        this.health = maxHealth; // start at full health
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    public void heal(int amount) {
        if (amount > 0) {
            this.health = Math.min(this.health + amount, this.maxHealth);
        }
    }

    public void damage(int amount) {
        if (amount > 0) {
            this.health = Math.max(this.health - amount, 0);
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

    public abstract String getDisplayName();

    // Apply a new effect. Duplicate of same type → refresh instead of stack.
    public String applyStatusEffect(StatusEffect newEffect) {
        for (StatusEffect e : effects) {
            if (e.getClass() == newEffect.getClass()) {
                e.refresh(newEffect.getRemainingTurns());
                return newEffect.getName() + " on " + getDisplayName() + " is refreshed.";
            }
        }
        effects.add(newEffect);
        return newEffect.onApply(this);
    }

    // Tick every effect once; collect messages; drop expired ones.
    public List<String> tickStatusEffects() {
        List<String> msgs = new ArrayList<>();
        Iterator<StatusEffect> it = effects.iterator();
        while (it.hasNext()) {
            StatusEffect e = it.next();
            String m = e.onTurn(this);
            if (m != null && !m.isEmpty()) msgs.add(m);
            if (e.isExpired()) {
                msgs.add(e.onExpire(this));
                it.remove();
            }
        }
        return msgs;
    }

    // Capability queries — here's where the interfaces pay off:
    public boolean isMovementBlocked() {
        for (StatusEffect e : effects) {
            if (e instanceof MovementBlocking) return true;
        }
        return false;
    }

    public int modifyOutgoingDamage(int base) {
        int dmg = base;
        for (StatusEffect e : effects) {
            if (e instanceof DamageModifying dm) dmg = dm.modifyDamage(dmg);
        }
        return Math.max(dmg, 0);
    }
}
