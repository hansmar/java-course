import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private int health;
    private final int maxHealth;
    private final List<Item> inventory;
    private boolean hasWon;

    public Player(int x, int y, int maxHealth) {
        super(x, y);
        this.maxHealth = maxHealth;
        this.health = maxHealth; // Start at full health
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

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    public void heal(int amount) {
        if (amount > 0) {
            this.health = Math.min(this.health + amount, this.maxHealth);
        }
    }

    public void damage(int amount) {
        if (amount > 0) {
            this.health = Math.max(this.health - amount, 0);
        }
    }

    public boolean isDead() {
        return this.health <= 0;
    }

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
        return new ArrayList<>(inventory); // Return defensive copy
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setWon() {
        this.hasWon = true;
    }
}