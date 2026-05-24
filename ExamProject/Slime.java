/**
 * Slime: slow tank. Lots of HP (60), low damage (4), but only acts every
 * other turn — the rest of the time it's "gathering itself" / resting.
 *
 * Demonstrates a new tick shape: per-entity state that persists across turns.
 * Zombie/Ghoul/CaveDog are stateless reactors (input: world; output: action).
 * Slime carries a counter that mutates each tick. The framework supports this
 * without any changes — Entity instances live as long as the Dungeon does.
 */
public class Slime extends Enemy {
    private boolean readyToAct;

    public Slime(int x, int y) {
        super(x, y, /*maxHealth*/ 60, /*attackDamage*/ 4);
        this.readyToAct = true; // act on the first turn we exist
    }

    @Override
    protected char getSymbol() { return 's'; }

    @Override
    protected Color getFg() { return Color.GREEN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    protected String getName() { return "slime"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        // Flip the flag every tick: act, rest, act, rest...
        readyToAct = !readyToAct;
        if (readyToAct) {
            // We just flipped TO ready — meaning this turn we rest. Symmetry
            // works out because we initialise to true and the first thing tick
            // does is flip it.
            return "";
        }

        if (isAdjacentTo(player.getX(), player.getY())) {
            player.damage(getAttackDamage());
            return "The slime engulfs you for " + getAttackDamage() + ".";
        }
        stepToward(dungeon, player, player.getX(), player.getY());
        return "";
    }
}
