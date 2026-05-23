import java.util.ArrayList;
import java.util.List;

public class Player extends LivingEntity {
    private final List<Item> inventory;
    private boolean hasWon;

    public Player(int x, int y, int maxHealth) {
        super(x, y, maxHealth);
        this.inventory = new ArrayList<>();
        this.hasWon = false;
    }

    // --- Entity Implementation ---

    @Override
    protected char getSymbol() { return '@'; }

    @Override
    protected Color getFg() { return Color.RED; }

    @Override
    protected Color getBg() { return Color.BLACK; }

    @Override
    public boolean isOccupying() {
        return true; // Player blocks other entities from moving into their square
    }

    @Override
    public String interactWith(Player player) {
        return "";
    }

    // --- Player Specific Logic ---
    // HP / heal / damage / isDead all inherited from LivingEntity.

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void addItem(Item item) {
        if (item != null) {
            this.inventory.add(item);
        }
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory); // defensive copy
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setWon() {
        this.hasWon = true;
    }
}
