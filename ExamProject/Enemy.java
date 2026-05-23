// Common base for hostile entities. Centralises the "player walks into me,
// we both take a swing" exchange so each concrete enemy only has to define
// its AI in tick() plus its stats and visuals.
public abstract class Enemy extends LivingEntity {
    private final int attackDamage;

    public Enemy(int x, int y, int maxHealth, int attackDamage) {
        super(x, y, maxHealth);
        this.attackDamage = attackDamage;
    }

    public int getAttackDamage() { return attackDamage; }

    @Override
    public boolean isOccupying() {
        return true; // enemies block — bumping them becomes melee, not movement
    }

    // Player walked into us. We hit them, they hit us back. Standard melee exchange.
    // Subclasses can override for special behavior (e.g. ranged enemies, poison),
    // but the default covers the common case the spec describes.
    @Override
    public String interactWith(Player player) {
        int playerDamage = player.getAttackDamage();
        damage(playerDamage);

        StringBuilder msg = new StringBuilder();
        msg.append("You strike the ").append(getName()).append(" for ").append(playerDamage).append(".");

        if (isDead()) {
            consume();
            msg.append(" It falls!");
        } else {
            // Counter-attack only if we survived.
            player.damage(attackDamage);
            msg.append(" The ").append(getName()).append(" hits you for ").append(attackDamage).append(".");
        }
        return msg.toString();
    }

    // Used for status messages. Subclasses provide a display name.
    protected abstract String getName();
}
