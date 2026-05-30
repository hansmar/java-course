public class Treasure extends Entity {

    public Treasure(int x, int y) {
        super(x, y);
    }

    @Override
    protected char getSymbol() { return '$'; }

    @Override
    protected Color getFg() { return Color.YELLOW; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public String interactWith(Player player) {
        player.setWon();
        consume();
        return "You found the treasure!";
    }
}