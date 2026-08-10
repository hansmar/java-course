public class FreezeEffect extends StatusEffect implements MovementBlocking {
    public FreezeEffect(int turns) {
        super("Freeze", turns);
    }

    @Override
    protected String applyEffect(LivingEntity target) {
        return target.getDisplayName() + " remain frozen solid.";
    }
}