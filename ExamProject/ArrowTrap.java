/*
 * Arrow trap. Each turn, scans the four cardinal directions for the player.
 */

public class ArrowTrap extends Entity {
    private final int damageAmount;

    public ArrowTrap(int x, int y, int damageAmount) {
        super(x, y);
        this.damageAmount = damageAmount;
    }

    @Override
    protected char getSymbol() { return '+'; }

    @Override
    protected Color getFg() { return Color.WHITE; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player can stand on the tile; trap fires from beside the wall
    }

    @Override
    public String interactWith(Player player) {
        // Point-blank: player walked onto us. Fire immediately and consume.
        player.damage(damageAmount);
        consume();
        return "Point-blank! An arrow shoots from the trap for " + damageAmount + " damage.";
    }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        if (isConsumed()) return ""; // belt-and-braces; Dungeon.tickAll already filters

        int dx = player.getX() - getX();
        int dy = player.getY() - getY();

        // Only fires when player shares the trap's row or column.
        if (dx != 0 && dy != 0) return "";
        if (dx == 0 && dy == 0) return ""; // shouldn't happen given isOccupying=false + interactWith handles step-on

        int sx = Integer.signum(dx);
        int sy = Integer.signum(dy);

        int checkX = getX() + sx;
        int checkY = getY() + sy;
        while (checkX != player.getX() || checkY != player.getY()) {
            Entity at = dungeon.entityAt(checkX, checkY);
            if (at != null && at.isOccupying()) {
                return ""; // line of sight blocked
            }
            checkX += sx;
            checkY += sy;
        }

        // Reached the player without hitting a blocker. Fire!
        player.damage(damageAmount);
        consume();
        return "An arrow shoots from the trap! " + damageAmount + " damage.";
    }
}
