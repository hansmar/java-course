import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DungeonLoader loader = new DungeonLoader();
        List<Dungeon> levels = new ArrayList<>();

        // Level files are loaded in order. The first level is where the player
        // starts; the last level should contain the treasure and no staircase.
        String[] levelFiles = { "levels/level1.txt", "levels/level2.txt" };

        try {
            for (String f : levelFiles) {
                levels.add(loader.loadFromFile(f));
            }
        } catch (IOException e) {
            System.err.println("Failed to load level file: " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            // Malformed level file (unknown character, empty file, etc.)
            System.err.println("Bad level data: " + e.getMessage());
            return;
        }

        Dungeon first = levels.get(0);
        Player player = new Player(
            first.getStartX(),
            first.getStartY(),
            /*maxHealth*/ 100,
            /*attackDamage*/ 25
        );

        Game game = new Game(levels, player);
        game.start();
    }
}
