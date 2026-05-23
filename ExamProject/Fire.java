public class Fire extends Entity {
    private final int damageAmount;
    private int interactionsRemaining;

    public Fire(int x, int y, int damageAmount) {
        super(x, y);
        this.damageAmount = damageAmount;
        this.interactionsRemaining = 3;
    }

    @Override
    protected char getSymbol() { return '^'; }

    // Color telegraphs remaining intensity. Three real states now:
    //   3 charges → yellow (hot/bright)
    //   2 charges → red   (burning down)
    //   1 charge  → magenta (embers, about to die)
    @Override
    protected Color getFg() {
        if (interactionsRemaining >= 3) return Color.YELLOW;
        if (interactionsRemaining == 2) return Color.RED;
        return Color.MAGENTA;
    }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player stands on the tile and gets burned
    }

    @Override
    public String interactWith(Player player) {
        player.damage(damageAmount);
        interactionsRemaining--;

        String msg = "You step into the flames and take " + damageAmount + " damage.";
        if (interactionsRemaining <= 0) {
            consume();
            msg += " The fire sputters out.";
        }
        return msg;
    }
}
