/**
 * A wall that can be broken. Extends LivingEntity because it has HP.
 */
public class DestructibleWall extends LivingEntity {

    public DestructibleWall(int x, int y, int maxHealth) {
        super(x, y, maxHealth);
    }

    @Override
    protected char getSymbol() { return '%'; }

    @Override
    protected Color getFg() { return Color.WHITE; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return true; // blocks until destroyed
    }

    @Override
    public String getDisplayName() {
        return "The wall";
    }

    @Override
    public String interactWith(Player player) {
        int dmg = player.getAttackDamage();
        damage(dmg);
        String msg = "You smash the wall for " + dmg + ".";
        if (isDead()) {
            consume();
            msg += " It crumbles!";
        }
        return msg;
    }
}
