/**
 * CaveDog: fast, frail, weak bite. Like a Ghoul in movement (two-step skirmisher)
 * but with very different combat numbers — 20 HP, 5 damage. A genuine
 * glass-cannon variant: closes fast but you can drop it in one player hit.
 *
 * Implementation note: CaveDog extends Enemy directly rather than Ghoul. They
 * share the *shape* of their AI (two steps, attack on adjacency) but the
 * shared logic lives in Enemy's stepToward/isAdjacentTo helpers, not in
 * Ghoul. Reusing Ghoul as a parent would have locked CaveDog into Ghoul's
 * stats, which defeats the purpose of having two distinct enemies.
 *
 * The lesson (worth the report): inheritance is one tool for code reuse,
 * helper methods are another. When two classes share AI shape but differ in
 * stats and presentation, helpers on a common ancestor beat sibling inheritance.
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
    protected String getName() { return "cave dog"; }

    @Override
    public String tick(Dungeon dungeon, Player player) {
        // Two steps per turn with adjacency-attack between them.
        // Same shape as Ghoul's tick; the duplication is honest — these are
        // peer enemies, not parent/child. If a *third* two-step enemy is ever
        // added, then it's worth factoring a TwoStepEnemy intermediate class.
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
