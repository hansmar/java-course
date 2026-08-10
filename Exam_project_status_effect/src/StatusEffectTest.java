import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class StatusEffectTest {

    // --- Burning: damages each turn ---

    @Test
    public void burningDamagesEachTurn() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new BurningEffect(3, 5));

        p.tickStatusEffects();   // 5 damage -> 95
        assertEquals(95, p.getHealth());

        p.tickStatusEffects();   // 5 damage -> 90
        assertEquals(90, p.getHealth());
    }

    @Test
    public void burningStopsAfterItsDuration() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new BurningEffect(2, 5));

        p.tickStatusEffects();   // turn 1 -> 95
        p.tickStatusEffects();   // turn 2 -> 90, effect ends here

        List<String> after = p.tickStatusEffects();   // turn 3, nothing left

        assertEquals(90, p.getHealth());   // no extra damage
        assertTrue(after.isEmpty());       // no messages, effect is gone
    }

    // --- Regeneration: heals each turn ---

    @Test
    public void regenerationHealsEachTurn() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(50);            // HP = 50
        p.applyStatusEffect(new RegenerationEffect(3, 10));

        p.tickStatusEffects();   // +10 -> 60
        assertEquals(60, p.getHealth());

        p.tickStatusEffects();   // +10 -> 70
        assertEquals(70, p.getHealth());
    }

    @Test
    public void regenerationDoesNotHealAboveMax() {
        Player p = new Player(0, 0, 100, 25);   // already full
        p.applyStatusEffect(new RegenerationEffect(3, 10));

        p.tickStatusEffects();   // would be 110, should cap at 100
        assertEquals(100, p.getHealth());
    }

    // --- Freeze: stops movement ---

    @Test
    public void freezeBlocksMovement() {
        Player p = new Player(0, 0, 100, 25);
        assertFalse(p.isMovementBlocked());

        p.applyStatusEffect(new FreezeEffect(2));
        assertTrue(p.isMovementBlocked());
    }

    @Test
    public void freezeWearsOff() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new FreezeEffect(1));
        assertTrue(p.isMovementBlocked());

        p.tickStatusEffects();   // one turn passes, freeze ends
        assertFalse(p.isMovementBlocked());
    }

    // --- Strength: changes damage dealt ---

    @Test
    public void strengthIncreasesDamage() {
        Player p = new Player(0, 0, 100, 25);
        assertEquals(25, p.modifyOutgoingDamage(25));   // no effect, unchanged

        p.applyStatusEffect(new StrengthEffect(3, 10));
        assertEquals(35, p.modifyOutgoingDamage(25));    // +10
    }

    @Test
    public void strengthWearsOff() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new StrengthEffect(1, 10));
        assertEquals(35, p.modifyOutgoingDamage(25));

        p.tickStatusEffects();   // buff ends
        assertEquals(25, p.modifyOutgoingDamage(25));
    }

    // --- Duplicate effect: refreshes, does not stack ---

    @Test
    public void duplicateBurningDoesNotStack() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new BurningEffect(3, 5));
        p.applyStatusEffect(new BurningEffect(3, 5));   // same effect again

        p.tickStatusEffects();   // if it stacked -> 90, if refreshed -> 95
        assertEquals(95, p.getHealth());
    }

    @Test
    public void duplicateGivesRefreshMessage() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new BurningEffect(3, 5));
        String second = p.applyStatusEffect(new BurningEffect(3, 5));

        assertTrue(second.toLowerCase().contains("refresh"));
    }

    // --- Messaging: expiry is announced ---

    @Test
    public void expiryProducesMessage() {
        Player p = new Player(0, 0, 100, 25);
        p.applyStatusEffect(new FreezeEffect(1));

        List<String> messages = p.tickStatusEffects();

        boolean sawExpiry = false;
        for (String m : messages) {
            if (m.toLowerCase().contains("wears off")) {
                sawExpiry = true;
            }
        }
        assertTrue(sawExpiry);
    }

    // --- Multiple different effects active at the same time ---

    @Test
    public void twoDifferentEffectsRunTogether() {
        Player p = new Player(0, 0, 100, 25);
        p.damage(50);            // HP = 50, so we can see healing too

        // Burning (damage) and Regeneration (heal) active at once.
        p.applyStatusEffect(new BurningEffect(3, 5));
        p.applyStatusEffect(new RegenerationEffect(3, 8));

        // One tick runs BOTH: -5 from burning, +8 from regen. Net +3.
        p.tickStatusEffects();   // 50 - 5 + 8 = 53
        assertEquals(53, p.getHealth());
    }

    @Test
    public void differentEffectsBothStayActive() {
        Player p = new Player(0, 0, 100, 25);

        // Freeze (blocks movement) and Strength (buffs damage) together.
        p.applyStatusEffect(new FreezeEffect(3));
        p.applyStatusEffect(new StrengthEffect(3, 10));

        // Both capabilities apply at the same time.
        assertTrue(p.isMovementBlocked());               // from freeze
        assertEquals(35, p.modifyOutgoingDamage(25));    // from strength
    }
}
