public class BurningEffect extends StatusEffect {
    private final int damagePerTurn;
    public BurningEffect(int turns, int damagePerTurn) {
        super("Burning", turns);
        this.damagePerTurn = damagePerTurn;
    }
    @Override
    protected String applyEffect(LivingEntity target) {
        target.damage(damagePerTurn);
        return target.getDisplayName() + " take " + damagePerTurn + " burning damage.";
    }
}