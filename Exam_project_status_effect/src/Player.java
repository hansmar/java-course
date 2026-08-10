import java.util.ArrayList;
import java.util.List;

public class Player extends LivingEntity {
    private final List<Item> inventory;
    private final int attackDamage;
    private boolean hasWon;
    private boolean wantsNextLevel;
    private LitBomb pendingBomb; // bomb to drop at end of this turn, or null

    public Player(int x, int y, int maxHealth, int attackDamage) {
        super(x, y, maxHealth);
        this.attackDamage = attackDamage;
        this.inventory = new ArrayList<>();
        this.hasWon = false;
        this.wantsNextLevel = false;
        this.pendingBomb = null;
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

    @Override
    public String getDisplayName() {
        return "You";
    }

    // --- Player Specific Logic ---
    // HP / heal / damage / isDead all inherited from LivingEntity.

    public int getAttackDamage() { return attackDamage; }

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

    public String useItem(int slot) {
        if (slot < 0 || slot >= inventory.size()) return null;
        Item item = inventory.remove(slot);
        return item.use(this);
    }

    public boolean hasWon() {
        return hasWon;
    }

    public void setWon() {
        this.hasWon = true;
    }

    public boolean wantsNextLevel() {
        return wantsNextLevel;
    }

    public void requestNextLevel() {
        this.wantsNextLevel = true;
    }

    public void clearNextLevelRequest() {
        this.wantsNextLevel = false;
    }

    public void stageBomb(LitBomb bomb) {
        this.pendingBomb = bomb;
    }

    public LitBomb takePendingBomb() {
        LitBomb b = this.pendingBomb;
        this.pendingBomb = null;
        return b;
    }
}
