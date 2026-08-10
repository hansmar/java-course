public class IndestructibleWall extends Entity {

    public IndestructibleWall(int x, int y) {
        super(x, y);
    }

    @Override
    protected char getSymbol() { return '#'; }

    @Override
    protected Color getFg() { return Color.YELLOW; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public String interactWith(Player player) {
        return "";
    }
}
