public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    protected char getSymbol() { return '#'; }

    @Override
    protected Color getFg() { return Color.YELLOW; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public void interactWith(Player player) {
        // Walls do nothing when bumped into.
    }
}