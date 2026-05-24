/**
 * A wall that can be broken. Extends LivingEntity because it has HP — which
 * is why there's no abstract `Wall` parent above IndestructibleWall and this
 * class. The two share the conceptual category "wall" but have completely
 * different superstructure (Entity vs LivingEntity), so forcing a common
 * abstract class would buy nothing.
 *
 * Combat shape mirrors Enemy: player bumps, takes a swing, wall takes damage,
 * wall says something. Wall doesn't hit back — it's a wall.
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
