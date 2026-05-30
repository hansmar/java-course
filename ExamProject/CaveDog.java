/**
 * CaveDog: fast, frail, weak bite. Like a Ghoul in movement (two-step skirmisher)
 * but with very different combat numbers — 20 HP, 5 damage. A genuine
 * glass-cannon variant: closes fast but you can drop it in one player hit.
 */
public class CaveDog extends Enemy {

    public CaveDog(int x, int y) {
        super(x, y, /*maxHealth*/ 20, /*attackDamage*/ 5);
    }

    @Override
    protected char getSymbol() { return 'd'; }

    @Override
    protected Color getFg() { return Color.YELLOW; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public String getName() { return "cave dog"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        for (int i = 0; i < 2; i++) {
            if (isAdjacentTo(player.getX(), player.getY())) {
                player.damage(getAttackDamage());
                String verb = (i == 0) ? "snaps at" : "lunges and bites";
                return "The cave dog " + verb + " you for " + getAttackDamage() + "!";
            }
            if (!stepToward(dungeon, player, player.getX(), player.getY())) {
                break;
            }
        }
        return "";
    }
}
