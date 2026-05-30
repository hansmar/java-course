public class Water extends Entity {
    private final int healAmount;

    public Water(int x, int y, int healAmount) {
        super(x, y);
        this.healAmount = healAmount;
    }

    @Override
    protected char getSymbol() { return '~'; }

    @Override
    protected Color getFg() { return Color.CYAN; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false;
    }

    @Override
    public String interactWith(Player player) {
        player.heal(healAmount);
        return "You drink from the water and recover " + healAmount + " HP.";
    }
}