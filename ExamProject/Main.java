public class Main {
    public static void main(String[] args) {
        int width = 20;
        int height = 10;
        
        Dungeon dungeon = new Dungeon(width, height);
        
        // Spawn bounding walls manually to test collision
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    dungeon.addEntity(new Wall(x, y));
                }
            }
        }

        // Inject some testing structural obstacles in the map space
        dungeon.addEntity(new Wall(5, 4));
        dungeon.addEntity(new Wall(5, 5));

        // Healing water
        dungeon.addEntity(new Water(3, 3, 25));

        // Fire — burns 3 times, then dies. 10 damage per step.
        dungeon.addEntity(new Fire(7, 5, 10));

        // Generic item pickup — a health potion sitting on the floor
        dungeon.addEntity(new ItemPickup(12, 3, new HealthPotion(30)));

        // A zombie. Walk near it and watch it close the gap, then attack.
        dungeon.addEntity(new Zombie(14, 2));

        // Treasure
        dungeon.addEntity(new Treasure(15, 6));

        // Place Player safely in the open dungeon center
        Player player = new Player(10, 5, /*maxHealth*/ 100, /*attackDamage*/ 25);

        // Turn on the simulation engine
        Game game = new Game(dungeon, player);
        game.start();
    }
}