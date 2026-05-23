public class Zombie extends Enemy {

    public Zombie(int x, int y) {
        super(x, y, /*maxHealth*/ 40, /*attackDamage*/ 8);
    }

    @Override
    protected char getSymbol() { return 'Z'; }

    @Override
    protected Color getFg() { return Color.GREEN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    protected String getName() { return "zombie"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        int dx = player.getX() - getX();
        int dy = player.getY() - getY();
        int manhattan = Math.abs(dx) + Math.abs(dy);

        // Adjacent (cardinal) — attack instead of moving.
        if (manhattan == 1) {
            player.damage(getAttackDamage());
            return "The zombie bites you for " + getAttackDamage() + ".";
        }

        // Not adjacent — try to close the gap. Greedy: step along whichever axis
        // has the larger remaining distance. If that axis is blocked, try the other —
        // but only if the other axis actually has a non-zero component, otherwise the
        // "fallback" is just (0, 0) and the zombie freezes in place.
        int primaryX = 0, primaryY = 0;
        if (Math.abs(dx) >= Math.abs(dy)) {
            primaryX = Integer.signum(dx);
        } else {
            primaryY = Integer.signum(dy);
        }

        if (tryStep(dungeon, player, primaryX, primaryY)) return "";

        // Primary blocked. Try the other axis — only if it's a real direction.
        int fallbackX = (primaryX != 0) ? 0 : Integer.signum(dx);
        int fallbackY = (primaryY != 0) ? 0 : Integer.signum(dy);
        if (fallbackX != 0 || fallbackY != 0) {
            tryStep(dungeon, player, fallbackX, fallbackY);
        }
        // If both axes are blocked (or only one axis exists and it's blocked),
        // the zombie just waits this turn. Greedy AI has limits; a proper BFS
        // would route around L-shaped obstacles. Documented in the report.
        return "";
    }

    // Attempts a move by (sx, sy). Returns true if the zombie actually moved.
    // Won't step onto the player (combat is handled by the adjacency check),
    // won't step onto blocking entities, won't step out of bounds.
    private boolean tryStep(Dungeon dungeon, Player player, int sx, int sy) {
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
