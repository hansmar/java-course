/**
 * Slime: slow tank. Lots of HP (60), low damage (4), but only acts every
 * other turn — the rest of the time it's "gathering itself" / resting.
 */

public class Slime extends Enemy {
    private boolean resting;

    public Slime(int x, int y) {
        super(x, y, /*maxHealth*/ 60, /*attackDamage*/ 4);
        this.resting = false;
    }

    @Override
    protected char getSymbol() { return 's'; }

    @Override
    protected Color getFg() { return Color.GREEN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public String getName() { return "slime"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        if (resting) {
            resting = false;
            return "";
        }

        // Act, then mark ourselves resting for next turn.
        String msg;
        if (isAdjacentTo(player.getX(), player.getY())) {
            player.damage(getAttackDamage());
            msg = "The slime engulfs you for " + getAttackDamage() + ".";
        } else {
            stepToward(dungeon, player, player.getX(), player.getY());
            msg = "";
        }
        resting = true;
        return msg;
    }
}
