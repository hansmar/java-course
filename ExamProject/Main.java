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

        // Place Player safely in the open dungeon center
        Player player = new Player(10, 5, 100);

        // Turn on the simulation engine
        Game game = new Game(dungeon, player);
        game.start();
    }
}

