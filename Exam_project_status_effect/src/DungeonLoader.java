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
 *   T   floor trap (15 damage, one-shot)
 *   +   arrow trap (20 damage, line-of-sight, one-shot)
 *   P   health potion pickup (restores 30 HP when used)
 *   B   bomb pickup (30 damage, 3-turn fuse, 3x3 blast radius)
 *   @   player start position (not an entity in the entity list)
 **/

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

            // --- Traps ---
            case 'T': return new FloorTrap(x, y, 15);
            case '+': return new ArrowTrap(x, y, 20);
            case 'F': return new FreezeTrap(x, y, 3);

            // --- Items ---
            case 'P': return new ItemPickup(x, y, new HealthPotion(30));
            case 'B': return new ItemPickup(x, y, new Bomb(/*damage*/ 30, /*fuse*/ 3));
            case 'S': return new ItemPickup(x, y, new StrengthPotion(5, 10));

            default:
                throw new IllegalArgumentException(
                    "Unknown level character '" + c + "' at line " + (y + 1) + ", column " + (x + 1)
                );
        }
    }
}
