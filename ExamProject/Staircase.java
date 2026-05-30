public class Staircase extends Entity {

    public Staircase(int x, int y) {
        super(x, y);
    }

    @Override
    protected char getSymbol() { return '>'; }

    @Override
    protected Color getFg() { return Color.CYAN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player walks onto the staircase to descend
    }

    @Override
    public String interactWith(Player player) {
        
        player.requestNextLevel();
        return "You descend the staircase...";
    }
}
