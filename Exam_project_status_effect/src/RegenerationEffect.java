public class RegenerationEffect extends StatusEffect {
    private final int healPerTurn;

    public RegenerationEffect(int turns, int healPerTurn) {
        super("Regeneration", turns);
        this.healPerTurn = healPerTurn;
    }

    @Override
    protected String applyEffect(LivingEntity target) {
        target.heal(healPerTurn);
        return target.getDisplayName() + " recover " + healPerTurn + " health.";
    }
}