/**
 * One-shot floor trap. Player steps on it, takes damage, trap is consumed.
 * Visible (symbol 'T') rather than hidden.
 */
public class FloorTrap extends Entity {
    private final int damageAmount;

    public FloorTrap(int x, int y, int damageAmount) {
        super(x, y);
        this.damageAmount = damageAmount;
    }

    @Override
    protected char getSymbol() { return 'T'; }

    @Override
    protected Color getFg() { return Color.RED; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player steps onto it to trigger
    }

    @Override
    public String interactWith(Player player) {
        player.damage(damageAmount);
        consume();
        return "A trap snaps shut! You take " + damageAmount + " damage.";
    }
}
