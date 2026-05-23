public abstract class Entity {
    protected int x;
    protected int y;

    private boolean consumed;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        this.consumed = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public boolean isConsumed() {
        return consumed;
    }

    public void consume() {
        this.consumed = true;
    }

    // Template Method Pattern: Subclasses define data; parent handles canvas writing.
    protected abstract char getSymbol();
    protected abstract Color getFg();
    protected abstract Color getBg();

    public final void renderOn(Canvas canvas) {
        canvas.set(this.x, this.y, getSymbol(), getFg(), getBg());
    }

    // Default to true: most entities (walls, enemies, hazards) block by default.
    public boolean isOccupying() {
        return true;
    }

    // Forced abstraction: every entity type must consciously declare its interaction rule.
    public abstract String interactWith(Player player);

    // Per-turn action. Default is no-op — walls, water, treasure, pickups don't act.
    // Only entities that need agency (enemies, traps with timers) override this.
    // Returns a message to append to the turn log (empty = silent).
    public String tick(Dungeon dungeon, Player player) {
        return "";
    }
}