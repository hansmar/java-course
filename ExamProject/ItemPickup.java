public class ItemPickup extends Entity {
    private final Item item;

    public ItemPickup(int x, int y, Item item) {
        super(x, y);
        this.item = item;
    }

    @Override
    protected char getSymbol() { return '?'; }

    @Override
    protected Color getFg() { return Color.MAGENTA; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return false; // player walks onto pickups
    }

    @Override
    public String interactWith(Player player) {
        player.addItem(item);
        consume();
        return "You picked up: " + item.getName();
    }
}
