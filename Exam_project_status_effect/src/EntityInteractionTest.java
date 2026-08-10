import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EntityInteractionTest {

    @Test
    public void waterHealsPlayer() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(40); // HP = 60
        Water w = new Water(0, 0, 20);
        w.interactWith(p);
        assertEquals(80, p.getHealth());
    }

    @Test
    public void waterIsNotBlocking() {
        // Player should be able to walk onto a water tile.
        assertFalse(new Water(0, 0, 20).isOccupying());
    }

    @Test
    public void fireDamagesOnFirstInteraction() {
        Player p = new Player(0, 0, 100, 25);
        Fire f = new Fire(0, 0, 10);
        f.interactWith(p);
        assertEquals(90, p.getHealth());
    }

    @Test
    public void fireExtinguishesAfterThreeInteractions() {
        Player p = new Player(0, 0, 100, 25);
        Fire f = new Fire(0, 0, 10);
        f.interactWith(p);
        assertFalse(f.isConsumed());
        f.interactWith(p);
        assertFalse(f.isConsumed());
        f.interactWith(p);
        assertTrue(f.isConsumed());
        assertEquals(70, p.getHealth());
    }

    @Test
    public void treasureSetsHasWon() {
        Player p = new Player(0, 0, 100, 25);
        Treasure t = new Treasure(0, 0);
        assertFalse(p.hasWon());
        t.interactWith(p);
        assertTrue(p.hasWon());
        assertTrue(t.isConsumed());
    }

    @Test
    public void staircaseRequestsNextLevel() {
        Player p = new Player(0, 0, 100, 25);
        Staircase s = new Staircase(0, 0);
        assertFalse(p.wantsNextLevel());
        s.interactWith(p);
        assertTrue(p.wantsNextLevel());
        // Staircase deliberately does NOT consume itself — backtracking, etc.
        assertFalse(s.isConsumed());
    }

    @Test
    public void floorTrapDamagesAndConsumes() {
        Player p = new Player(0, 0, 100, 25);
        FloorTrap trap = new FloorTrap(0, 0, 15);
        trap.interactWith(p);
        assertEquals(85, p.getHealth());
        assertTrue(trap.isConsumed());
    }

    @Test
    public void itemPickupTransfersItemAndConsumes() {
        Player p = new Player(0, 0, 100, 25);
        ItemPickup pickup = new ItemPickup(0, 0, new HealthPotion(20));
        pickup.interactWith(p);
        assertEquals(1, p.getInventory().size());
        assertTrue(pickup.isConsumed());
    }

    @Test
    public void wallIsBlockingAndDoesNothing() {
        Player p = new Player(0, 0, 100, 25);
        IndestructibleWall w = new IndestructibleWall(0, 0);
        String msg = w.interactWith(p);
        // Wall returns empty string so Game can fall through to "Ouch! ..." default.
        assertEquals("", msg);
        assertTrue(w.isOccupying());
        assertEquals(100, p.getHealth()); // wall doesn't hurt you
    }
}