public class HealthPotion extends Item {
    private final int restoreAmount;

    public HealthPotion(int restoreAmount) {
        super("Health Potion");
        this.restoreAmount = restoreAmount;
    }

    @Override
    public String use(Player player) {
        player.heal(restoreAmount);
        return "You quaff the potion and recover " + restoreAmount + " HP.";
    }
}
