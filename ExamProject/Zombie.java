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
    public String getName() { return "zombie"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        // Adjacent — attack instead of moving.
        if (isAdjacentTo(player.getX(), player.getY())) {
            player.damage(getAttackDamage());
            return "The zombie bites you for " + getAttackDamage() + ".";
        }
        // Otherwise close the gap by one step.
        stepToward(dungeon, player, player.getX(), player.getY());
        return "";
    }
}
