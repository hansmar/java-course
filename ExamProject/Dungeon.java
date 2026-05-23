import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private final List<Entity> entities;
    private final int width;
    private final int height;

    public Dungeon(int width, int height) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public void addEntity(Entity e) {
        if (e != null) {
            entities.add(e);
        }
    }

    // Finds an entity at a given position, or null if empty
    public Entity entityAt(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                return e;
            }
        }
        return null;
    }

    // Let the dungeon delegate visual rendering across all registered entities
    public void renderOn(Canvas canvas) {
        for (Entity e : entities) {
            e.renderOn(canvas);
        }
    }

    // Clean up entities marked for deletion at the end of the turn (for later items/traps)
    public void cleanup() {
        // We can hook in entities.removeIf(...) later when we add life-cycle states.
    }
}