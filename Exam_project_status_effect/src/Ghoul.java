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
    public String getName() { return "ghoul"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        // Two movement points per turn. After each step, re-check adjacency.
        for (int i = 0; i < 2; i++) {
            if (isAdjacentTo(player.getX(), player.getY())) {
                player.damage(getAttackDamage());
                String verb = (i == 0) ? "rends" : "leaps in and rends";
                return "The ghoul " + verb + " you for " + getAttackDamage() + "!";
            }
            if (!stepToward(dungeon, player, player.getX(), player.getY())) {
                break;
            }
        }
        return "";
    }
}
