public class StrengthEffect extends StatusEffect implements DamageModifying {
    private final int bonus;

    public StrengthEffect(int turns, int bonus) {
        super("Strength", turns);
        this.bonus = bonus;
    }

    @Override
    protected String applyEffect(LivingEntity target) {
        return "";
    }

    @Override
    public int modifyDamage(int baseDamage) {
        return baseDamage + bonus;
    }
}