public class StrengthPotion extends Item {
    private final int bonus;
    private final int turns;

    public StrengthPotion(int turns, int bonus) {
        super("Strength Potion");
        this.turns = turns;
        this.bonus = bonus;
    }

    @Override
    public String use(Player player) {
        return player.applyStatusEffect(new StrengthEffect(turns, bonus));
    }
}