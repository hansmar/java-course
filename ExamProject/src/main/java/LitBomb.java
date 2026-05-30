import java.util.ArrayList;
import java.util.List;

/**
 * Bomb that has been placed on the floor with a burning fuse. Counts down
 * each turn, then explodes in a 3x3 area centered on itself, damaging every
 * LivingEntity (player and enemies alike — bombs are not friendly fire-safe)
 * within range.
 */

public class LitBomb extends Entity {
    private final int damageAmount;
    private int fuseTurnsRemaining;

    public LitBomb(int x, int y, int damageAmount, int fuseTurns) {
        super(x, y);
        this.damageAmount = damageAmount;
        this.fuseTurnsRemaining = fuseTurns;
    }

    public int getFuseTurnsRemaining() { return fuseTurnsRemaining; }

    @Override
    protected char getSymbol() { return 'b'; }

    @Override
    protected Color getFg() {
        if (fuseTurnsRemaining <= 1) return Color.RED;
        if (fuseTurnsRemaining == 2) return Color.YELLOW;
        return Color.WHITE;
    }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player can step over it (and probably should, fast)
    }

    @Override
    public String interactWith(Player player) {
        return "You step over the bomb. The fuse hisses.";
    }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        fuseTurnsRemaining--;
        if (fuseTurnsRemaining > 0) {
            // Only narrate the final tick to avoid spam on longer fuses.
            if (fuseTurnsRemaining == 1) {
                return "The bomb's fuse is about to burn out!";
            }
            return "";
        }

        StringBuilder msg = new StringBuilder("BOOM! The bomb explodes!");
        List<String> casualties = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int tx = getX() + dx;
                int ty = getY() + dy;

                if (player.getX() == tx && player.getY() == ty) {
                    player.damage(damageAmount);
                    casualties.add("you take " + damageAmount);
                }

                Entity at = dungeon.entityAt(tx, ty);
                if (at == null || at == this) continue;
                if (at.isConsumed()) continue; // already dead — don't blast corpses

                if (at instanceof LivingEntity living) {
                    living.damage(damageAmount);
                    String name = (at instanceof Enemy enemy) ? enemy.getName() : "wall";
                    casualties.add(name + " hit");
                    if (living.isDead()) {
                        at.consume();
                    }
                }
            }
        }

        if (!casualties.isEmpty()) {
            msg.append(" (");
            for (int i = 0; i < casualties.size(); i++) {
                if (i > 0) msg.append(", ");
                msg.append(casualties.get(i));
            }
            msg.append(")");
        }

        consume();
        return msg.toString();
    }
}
