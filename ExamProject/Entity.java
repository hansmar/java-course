public abstract class Entity {
    protected int x;
    protected int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

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
    public abstract void interactWith(Player player);
}