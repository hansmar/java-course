public class Ghoul extends Enemy {

    public Ghoul(int x, int y) {
        super(x, y, /*maxHealth*/ 25, /*attackDamage*/ 12);
    }

    @Override
    protected char getSymbol() { return 'G'; }

    @Override
    protected Color getFg() { return Color.MAGENTA; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    protected String getName() { return "ghoul"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        // Two movement points per turn. After each step, re-check adjacency:
        // a fast enemy that closes into melee mid-turn should attack with what's
        // left of the turn, not waste motion stepping past or onto the player.
        for (int i = 0; i < 2; i++) {
            if (isAdjacentTo(player.getX(), player.getY())) {
                player.damage(getAttackDamage());
                String verb = (i == 0) ? "rends" : "leaps in and rends";
                return "The ghoul " + verb + " you for " + getAttackDamage() + "!";
            }
            // Not adjacent — try to close. If we can't move (blocked both axes),
            // give up on the second step too — sitting still on the second step
            // wouldn't change anything.
            if (!stepToward(dungeon, player, player.getX(), player.getY())) {
                break;
            }
        }
        return "";
    }
}
