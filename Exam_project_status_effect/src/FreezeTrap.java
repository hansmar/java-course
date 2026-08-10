public class FreezeTrap extends Entity {
    private final int freezeTurns;

    public FreezeTrap(int x, int y, int freezeTurns) {
        super(x, y);
        this.freezeTurns = freezeTurns;
    }

    @Override
    protected char getSymbol() { return 'F'; }

    @Override
    protected Color getFg() { return Color.CYAN; }   // icy blue

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player steps onto it to trigger
    }

    @Override
    public String interactWith(Player player) {
        consume();
        String msg = "A freezing trap triggers! ";
        return msg + player.applyStatusEffect(new FreezeEffect(freezeTurns));
    }
}