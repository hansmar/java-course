import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void newPlayerStartsAtFullHealth() {
        Player p = new Player(0, 0, 100, 25);
        assertEquals(100, p.getHealth());
        assertEquals(100, p.getMaxHealth());
        assertFalse(p.isDead());
    }

    @Test
    public void healDoesNotExceedMaxHealth() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(20);   // HP = 80
        p.heal(50);     // would be 130, should cap at 100
        assertEquals(100, p.getHealth());
    }

    @Test
    public void damageDoesNotGoNegative() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(999);
        assertEquals(0, p.getHealth());
        assertTrue(p.isDead());
    }

    @Test
    public void isDeadWhenHealthIsZero() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(100);
        assertTrue(p.isDead());
    }

    @Test
    public void useItemOnEmptyInventoryReturnsNull() {
        Player p = new Player(0, 0, 100, 25);
        assertNull(p.useItem(0));
    }

    @Test
    public void useItemRemovesItFromInventory() {
        Player p = new Player(0, 0, 100, 25);
        p.addItem(new HealthPotion(20));
        assertEquals(1, p.getInventory().size());
        p.useItem(0);
        assertEquals(0, p.getInventory().size());
    }

    @Test
    public void useHealthPotionRestoresHealth() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(50);   // HP = 50
        p.addItem(new HealthPotion(30));
        String msg = p.useItem(0);
        assertEquals(80, p.getHealth());
        assertNotNull(msg);
    }

    @Test
    public void getInventoryReturnsDefensiveCopy() {
        Player p = new Player(0, 0, 100, 25);
        p.addItem(new HealthPotion(20));
        List<Item> inv = p.getInventory();
        inv.clear();
        // Mutating the returned list must not affect the player.
        assertEquals(1, p.getInventory().size());
    }

    @Test
    public void addNullItemIsIgnored() {
        Player p = new Player(0, 0, 100, 25);
        p.addItem(null);
        assertEquals(0, p.getInventory().size());
    }

    @Test
    public void moveToUpdatesPosition() {
        Player p = new Player(5, 5, 100, 25);
        p.moveTo(10, 12);
        assertEquals(10, p.getX());
        assertEquals(12, p.getY());
    }
}