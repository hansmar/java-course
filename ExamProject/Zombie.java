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
        // has the larger remaining distance. If that axis is blocked, try the other.
        int stepX = 0;
        int stepY = 0;
        if (Math.abs(dx) >= Math.abs(dy)) {
            stepX = Integer.signum(dx);
        } else {
            stepY = Integer.signum(dy);
        }

        if (tryStep(dungeon, player, stepX, stepY)) return "";

        // Preferred axis blocked. Try the other axis.
        if (stepX != 0) {
            stepX = 0;
            stepY = Integer.signum(dy);
        } else {
            stepY = 0;
            stepX = Integer.signum(dx);
        }
        tryStep(dungeon, player, stepX, stepY);
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
