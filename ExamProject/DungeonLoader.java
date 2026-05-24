import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Reads a level from a text file and produces a Dungeon.
 *
 * Map symbols:
 *   #   indestructible wall
 *   %   destructible wall (40 HP, breakable with attacks)
 *   .   empty floor
 *   ~   water (heals 25)
 *   ^   fire (10 damage, 3 charges)
 *   Z   zombie  (40 HP,  8 dmg, 1 step/turn)
 *   G   ghoul   (25 HP, 12 dmg, 2 steps/turn — fast, fragile)
 *   d   cave dog (20 HP, 5 dmg, 2 steps/turn — weak fast)
 *   s   slime   (60 HP,  4 dmg, acts every other turn — slow tank)
 *   >   staircase (descend to next level)
 *   $   treasure (win condition)
 *   P   health potion pickup (restores 30 HP when used)
 *   @   player start position (not an entity in the entity list)
 *
 * The first '@' encountered sets the start position. Subsequent '@' characters
 * are treated as floor — only one start per level.
 *
 * Width is taken from the longest line; shorter lines are right-padded with floor.
 *
 * Errors:
 *   - IOException on read failure (file not found, permission, etc.)
 *   - IllegalArgumentException on unknown characters or empty file. The message
 *     includes line and column so malformed levels are debuggable.
 */
public class DungeonLoader {

    public Dungeon loadFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Level file is empty: " + path);
        }

        int height = lines.size();
        int width = 0;
        for (String line : lines) {
            if (line.length() > width) width = line.length();
        }
        if (width == 0) {
            throw new IllegalArgumentException("Level file has no content: " + path);
        }

        // Two-pass: first find the start position so we can construct the Dungeon
        // with it. (Dungeon's start position is final.) Second pass populates entities.
        int startX = -1, startY = -1;
        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == '@') {
                    if (startX == -1) {
                        startX = x;
                        startY = y;
                    }
                    // duplicate '@' silently ignored — only the first counts
                }
            }
        }
        if (startX == -1) {
            // Missing @ = malformed level file. Consistent with how we treat
            // unknown characters: throw with a clear message, let Main report it,
            // exit cleanly. Silently teleporting to (1,1) was a hedge that could
            // drop the player into a wall.
            throw new IllegalArgumentException(
                "Level file '" + path + "' has no player start position ('@')"
            );
        }

        Dungeon dungeon = new Dungeon(width, height, startX, startY);

        for (int y = 0; y < height; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Entity e = symbolToEntity(c, x, y);
                if (e != null) {
                    dungeon.addEntity(e);
                }
                // null = pure floor (or player start), nothing to add
            }
        }
        return dungeon;
    }

    /**
     * Maps a single map character to an Entity, or returns null for "no entity
     * here" (floor, player start, or padding). Unknown characters throw — silent
     * failure on a typo'd level file would be miserable to debug.
     */
    private Entity symbolToEntity(char c, int x, int y) {
        switch (c) {
            case '.':
            case '@':
            case ' ': // tolerate trailing/padding whitespace as floor
                return null;

            // --- Walls ---
            case '#': return new IndestructibleWall(x, y);
            case '%': return new DestructibleWall(x, y, 40);

            // --- Terrain / hazards ---
            case '~': return new Water(x, y, 25);
            case '^': return new Fire(x, y, 10);

            // --- Enemies ---
            case 'Z': return new Zombie(x, y);
            case 'G': return new Ghoul(x, y);
            case 'd': return new CaveDog(x, y);
            case 's': return new Slime(x, y);

            // --- Special ---
            case '>': return new Staircase(x, y);
            case '$': return new Treasure(x, y);

            // --- Items ---
            // Each item type gets its own symbol. Add new items below as they're
            // implemented. Keep this section grouped so it's easy to scan.
            case 'P': return new ItemPickup(x, y, new HealthPotion(30));
            // case 'B': return new ItemPickup(x, y, new Bomb(...));  // TODO when Bomb exists

            default:
                throw new IllegalArgumentException(
                    "Unknown level character '" + c + "' at line " + (y + 1) + ", column " + (x + 1)
                );
        }
    }
}
