import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LitBombTest {

    @Test
    public void bombDoesNotExplodeBeforeFuseExpires() {
        Dungeon d = new Dungeon(10, 10);
        Player p = new Player(5, 5, 100, 25); // adjacent to bomb
        LitBomb bomb = new LitBomb(5, 4, 30, /*fuse*/ 3);
        d.addEntity(bomb);

        // First tick: fuse goes 3 -> 2. No explosion.
        bomb.tick(d, p);
        assertEquals(100, p.getHealth());
        assertFalse(bomb.isConsumed());
    }

    @Test
    public void bombDamagesPlayerInRange() {
        Dungeon d = new Dungeon(10, 10);
        Player p = new Player(5, 5, 100, 25);
        LitBomb bomb = new LitBomb(5, 4, 30, /*fuse*/ 1); // adjacent to player
        d.addEntity(bomb);

        // One tick takes fuse 1 -> 0 -> explode.
        bomb.tick(d, p);
        assertEquals(70, p.getHealth());        // 100 - 30
        assertTrue(bomb.isConsumed());
    }

    @Test
    public void bombDoesNotDamagePlayerOutOfRange() {
        Dungeon d = new Dungeon(10, 10);
        Player p = new Player(0, 0, 100, 25);   // far corner
        LitBomb bomb = new LitBomb(8, 8, 30, /*fuse*/ 1); // opposite corner
        d.addEntity(bomb);

        bomb.tick(d, p);
        assertEquals(100, p.getHealth());       // untouched
        assertTrue(bomb.isConsumed());          // still exploded, just nobody in range
    }

    @Test
    public void bombDamagesEnemyInRange() {
        Dungeon d = new Dungeon(10, 10);
        Player p = new Player(0, 0, 100, 25);   // out of range
        Zombie z = new Zombie(5, 5);            // adjacent to bomb
        d.addEntity(z);
        LitBomb bomb = new LitBomb(5, 4, 30, 1);
        d.addEntity(bomb);

        bomb.tick(d, p);
        assertEquals(10, z.getHealth());        // 40 - 30
        assertEquals(100, p.getHealth());       // player still safe
    }
}