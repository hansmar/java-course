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

    // Run a turn for every entity. Returns each entity's non-empty message
    // so Game can format them for the status line. Game/display concerns
    // stay out of Dungeon.
    public List<String> tickAll(Player player) {
        List<String> messages = new ArrayList<>();
        // Iterate a snapshot: a tick may add or remove entities (e.g. a future
        // bomb explosion), which would ConcurrentModificationException on the live list.
        for (Entity e : new ArrayList<>(entities)) {
            // Skip entities killed earlier this turn — dead enemies don't get a swing.
            if (e.isConsumed()) continue;
            String msg = e.tick(this, player);
            if (msg != null && !msg.isEmpty()) {
                messages.add(msg);
            }
        }
        return messages;
    }

    // Clean up entities marked for deletion at the end of the turn (for later items/traps)
    public void cleanup() {
        entities.removeIf(Entity::isConsumed);
    }
}