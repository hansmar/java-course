/**
 * Inventory representation of a bomb. Using it stages a LitBomb.
 */
public class Bomb extends Item {
    private final int damageAmount;
    private final int fuseTurns;

    public Bomb(int damageAmount, int fuseTurns) {
        super("Bomb");
        this.damageAmount = damageAmount;
        this.fuseTurns = fuseTurns;
    }

    @Override
    public String use(Player player) {
        LitBomb lit = new LitBomb(player.getX(), player.getY(), damageAmount, fuseTurns);
        player.stageBomb(lit);
        return "You light the bomb's fuse and drop it. Run!";
    }
}
