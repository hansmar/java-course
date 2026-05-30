public abstract class Item {
    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Returns a message describing what happened when the item was used.
    public abstract String use(Player player);
}
