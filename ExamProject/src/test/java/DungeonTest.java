import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DungeonTest {

    @Test
    public void entityAtReturnsEntityAtPosition() {
        Dungeon d = new Dungeon(10, 10);
        IndestructibleWall w = new IndestructibleWall(3, 4);
        d.addEntity(w);
        assertSame(w, d.entityAt(3, 4));
    }

    @Test
    public void entityAtReturnsNullForEmptyTile() {
        Dungeon d = new Dungeon(10, 10);
        d.addEntity(new IndestructibleWall(3, 4));
        assertNull(d.entityAt(0, 0));
    }

    @Test
    public void cleanupRemovesConsumedEntities() {
        Dungeon d = new Dungeon(10, 10);
        Treasure t = new Treasure(5, 5);
        d.addEntity(t);
        t.consume();
        d.cleanup();
        assertNull(d.entityAt(5, 5));
    }

    @Test
    public void cleanupKeepsLiveEntities() {
        Dungeon d = new Dungeon(10, 10);
        IndestructibleWall w = new IndestructibleWall(5, 5);
        d.addEntity(w);
        d.cleanup();
        assertSame(w, d.entityAt(5, 5));
    }

    @Test
    public void getEntitiesSnapshotIsDefensiveCopy() {
        Dungeon d = new Dungeon(10, 10);
        d.addEntity(new IndestructibleWall(0, 0));
        List<Entity> snap = d.getEntitiesSnapshot();
        snap.clear();
        // Mutating the snapshot must not affect the dungeon.
        assertEquals(1, d.getEntitiesSnapshot().size());
    }

    @Test
    public void startPositionIsStoredAndRetrievable() {
        Dungeon d = new Dungeon(20, 10, 7, 3);
        assertEquals(7, d.getStartX());
        assertEquals(3, d.getStartY());
    }
}