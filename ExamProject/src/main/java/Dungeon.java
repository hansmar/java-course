import java.util.ArrayList;
import java.util.List;

public class Dungeon {
    private final List<Entity> entities;
    private final int width;
    private final int height;
    private final int startX;
    private final int startY;

    public Dungeon(int width, int height, int startX, int startY) {
        this.width = width;
        this.height = height;
        this.startX = startX;
        this.startY = startY;
        this.entities = new ArrayList<>();
    }

    public Dungeon(int width, int height) {
        this(width, height, 1, 1);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getStartX() { return startX; }
    public int getStartY() { return startY; }

    public void addEntity(Entity e) {
        if (e != null) {
            entities.add(e);
        }
    }

    public List<Entity> getEntitiesSnapshot() {
        return new ArrayList<>(entities);
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

    public List<String> tickAll(Player player) {
        List<String> messages = new ArrayList<>();

        for (Entity e : new ArrayList<>(entities)) {
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