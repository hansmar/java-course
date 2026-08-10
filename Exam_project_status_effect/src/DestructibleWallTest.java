import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DestructibleWallTest {

    @Test
    public void wallTakesDamageOnBump() {
        Player p = new Player(0, 0, 100, 25);
        DestructibleWall w = new DestructibleWall(0, 0, 40);
        w.interactWith(p);
        assertEquals(15, w.getHealth());
        assertFalse(w.isConsumed());
    }

    @Test
    public void wallCrumblesAtZeroHp() {
        Player p = new Player(0, 0, 100, 25);
        DestructibleWall w = new DestructibleWall(0, 0, 40);
        w.interactWith(p); // 15 left
        w.interactWith(p); // dead
        assertTrue(w.isDead());
        assertTrue(w.isConsumed());
    }

    @Test
    public void wallDoesNotHitBack() {
        Player p = new Player(0, 0, 100, 25);
        DestructibleWall w = new DestructibleWall(0, 0, 40);
        w.interactWith(p);
        assertEquals(100, p.getHealth());
    }

    @Test
    public void destructibleWallIsBlocking() {
        assertTrue(new DestructibleWall(0, 0, 40).isOccupying());
    }
}