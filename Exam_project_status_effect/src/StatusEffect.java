public abstract class StatusEffect {
    private final String name;
    private int remainingTurns;

    protected StatusEffect(String name, int durationTurns) {
        this.name = name;
        this.remainingTurns = durationTurns;
    }

    public String getName() { return name; }
    public int getRemainingTurns() { return remainingTurns; }
    public boolean isExpired() { return remainingTurns <= 0; }

    // Called once, when the effect is first applied. Message only.
    public String onApply(LivingEntity target) {
        return name + " afflicts " + target.getDisplayName() + ".";
    }

    // Called every turn. Decrements the counter, then does the per-turn effect..
    public final String onTurn(LivingEntity target) {
        remainingTurns--;
        return applyEffect(target);
    }

    public String onExpire(LivingEntity target) {
        return name + " wears off.";
    }

    protected abstract String applyEffect(LivingEntity target);

    // Duplicate handling: refresh duration to the longer of the two.
    public void refresh(int durationTurns) {
        this.remainingTurns = Math.max(this.remainingTurns, durationTurns);
    }
}