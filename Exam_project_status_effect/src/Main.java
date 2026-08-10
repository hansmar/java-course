import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String[] LEVEL_FILES = {
        "levels/level1.txt",
        "levels/level2.txt",
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Game game = buildGame(scanner);
            if (game == null) return; // load failure already reported

            game.start();

            if (!game.endedNaturally()) break;

            System.out.print("\nPress R to restart, any other key to quit: ");
            String line = scanner.nextLine().trim().toLowerCase();
            if (!line.equals("r")) break;
            System.out.println();
        }
    }
    
    private static Game buildGame(Scanner scanner) {
        DungeonLoader loader = new DungeonLoader();
        List<Dungeon> levels = new ArrayList<>();

        try {
            for (String f : LEVEL_FILES) {
                levels.add(loader.loadFromFile(f));
            }
        } catch (IOException e) {
            System.err.println("Failed to load level file: " + e.getMessage());
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("Bad level data: " + e.getMessage());
            return null;
        }

        Dungeon first = levels.get(0);
        Player player = new Player(
            first.getStartX(),
            first.getStartY(),
            /*maxHealth*/ 100,
            /*attackDamage*/ 25
        );

        return new Game(levels, player, scanner);
    }
}
