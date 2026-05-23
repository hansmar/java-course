// Shared base for anything with hit points: the player and all enemies.
// Sits between Entity and Player/Enemy so HP code isn't duplicated and
public abstract class LivingEntity extends Entity {
    private int health;
    private final int maxHealth;

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
}
