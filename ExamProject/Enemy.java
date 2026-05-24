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

    // ---- Shared AI helpers ----
    // Lifted from Zombie so every melee enemy can use the same greedy approach.
    // Subclasses build their tick() out of these primitives.

    /**
     * Manhattan-adjacency check. Returns true if (tx, ty) is exactly one step
     * away in a cardinal direction (no diagonals — matches our movement scheme).
     */
    protected boolean isAdjacentTo(int tx, int ty) {
        return Math.abs(getX() - tx) + Math.abs(getY() - ty) == 1;
    }

    /**
     * Take one greedy step toward (targetX, targetY). Picks the axis with the
     * larger remaining distance first, falls back to the other axis if blocked,
     * waits if both are blocked or only one axis exists and it's blocked.
     *
     * Returns true if the enemy actually moved. Greedy AI has known limits —
     * documented in the report: it can stall against concave obstacles.
     */
    protected boolean stepToward(Dungeon dungeon, Player player, int targetX, int targetY) {
        int dx = targetX - getX();
        int dy = targetY - getY();

        int primaryX = 0, primaryY = 0;
        if (Math.abs(dx) >= Math.abs(dy)) {
            primaryX = Integer.signum(dx);
        } else {
            primaryY = Integer.signum(dy);
        }

        if (tryStep(dungeon, player, primaryX, primaryY)) return true;

        int fallbackX = (primaryX != 0) ? 0 : Integer.signum(dx);
        int fallbackY = (primaryY != 0) ? 0 : Integer.signum(dy);
        if (fallbackX != 0 || fallbackY != 0) {
            return tryStep(dungeon, player, fallbackX, fallbackY);
        }
        return false;
    }

    /**
     * Attempt a single-tile move by (sx, sy). Refuses out-of-bounds, refuses
     * to land on the player (combat is handled by adjacency in tick), refuses
     * to land on any blocking entity. Returns true iff the enemy moved.
     */
    protected boolean tryStep(Dungeon dungeon, Player player, int sx, int sy) {
        if (sx == 0 && sy == 0) return false;
        int nx = getX() + sx;
        int ny = getY() + sy;

        if (nx < 0 || nx >= dungeon.getWidth() || ny < 0 || ny >= dungeon.getHeight()) {
            return false;
        }
        if (player.getX() == nx && player.getY() == ny) {
            return false; // don't displace the player; adjacency handles combat
        }
        Entity at = dungeon.entityAt(nx, ny);
        if (at != null && at.isOccupying()) return false;

        this.x = nx;
        this.y = ny;
        return true;
    }
}
