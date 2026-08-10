import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyCombatTest {

    @Test
    public void playerHitsEnemyAndTakesCounterHit() {
        Player p = new Player(0, 0, 100, 25);
        Zombie z = new Zombie(1, 0); // HP 40, attack 8
        z.interactWith(p);
        assertEquals(15, z.getHealth());  // 40 - 25
        assertEquals(92, p.getHealth());  // 100 - 8 counter
    }

    @Test
    public void enemyDiesWhenHpReachesZero() {
        Player p = new Player(0, 0, 100, 50); // big attack
        Zombie z = new Zombie(0, 0);
        z.interactWith(p); // 40 - 50 = lethal
        assertTrue(z.isDead());
        assertTrue(z.isConsumed());
    }

    @Test
    public void deadEnemyDoesNotCounterAttack() {
        // 50 attack guarantees lethal first hit on a 40-HP zombie.
        Player p = new Player(0, 0, 100, 50);
        Zombie z = new Zombie(0, 0);
        z.interactWith(p);
        // Player took no damage because the zombie died before counter-attacking.
        assertEquals(100, p.getHealth());
    }

    @Test
    public void enemyIsBlocking() {
        // Enemies block — bumping them is melee, not movement.
        assertTrue(new Zombie(0, 0).isOccupying());
        assertTrue(new Ghoul(0, 0).isOccupying());
        assertTrue(new CaveDog(0, 0).isOccupying());
        assertTrue(new Slime(0, 0).isOccupying());
    }

    @Test
    public void ghoulHitsHarderThanZombie() {
        // Confirms our stat-differentiation works through Enemy's interactWith.
        Player p1 = new Player(0, 0, 100, 1); // tiny attack so they survive
        Player p2 = new Player(0, 0, 100, 1);
        new Zombie(0, 0).interactWith(p1);
        new Ghoul(0, 0).interactWith(p2);
        // Zombie hits for 8, Ghoul for 12.
        assertTrue(p2.getHealth() < p1.getHealth());
    }
}