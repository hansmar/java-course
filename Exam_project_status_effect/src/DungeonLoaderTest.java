import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class DungeonLoaderTest {

    @TempDir
    Path tempDir;

    private Path writeLevel(String name, String content) throws IOException {
        Path file = tempDir.resolve(name);
        Files.writeString(file, content);
        return file;
    }

    @Test
    public void loadsAValidLevelFile() throws IOException {
        Path f = writeLevel("ok.txt",
            "#####\n" +
            "#@..#\n" +
            "#..Z#\n" +
            "#####\n");
        DungeonLoader loader = new DungeonLoader();
        Dungeon d = loader.loadFromFile(f.toString());

        assertEquals(5, d.getWidth());
        assertEquals(4, d.getHeight());
        // @ is at (1, 1)
        assertEquals(1, d.getStartX());
        assertEquals(1, d.getStartY());
        // Zombie at (3, 2)
        assertTrue(d.entityAt(3, 2) instanceof Zombie);
        // Wall at (0, 0)
        assertTrue(d.entityAt(0, 0) instanceof IndestructibleWall);
    }

    @Test
    public void unknownCharacterThrows() throws IOException {
        Path f = writeLevel("bad.txt",
            "###\n" +
            "#@X\n" + // X is not a defined symbol
            "###\n");
        DungeonLoader loader = new DungeonLoader();
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> loader.loadFromFile(f.toString())
        );
        assertTrue(ex.getMessage().contains("X"));
    }

    @Test
    public void missingPlayerStartThrows() throws IOException {
        Path f = writeLevel("nostart.txt",
            "###\n" +
            "#.#\n" +
            "###\n");
        DungeonLoader loader = new DungeonLoader();
        assertThrows(
            IllegalArgumentException.class,
            () -> loader.loadFromFile(f.toString())
        );
    }

    @Test
    public void missingFileThrowsIOException() {
        DungeonLoader loader = new DungeonLoader();
        assertThrows(
            IOException.class,
            () -> loader.loadFromFile(tempDir.resolve("does-not-exist.txt").toString())
        );
    }

    @Test
    public void emptyFileThrows() throws IOException {
        Path f = writeLevel("empty.txt", "");
        DungeonLoader loader = new DungeonLoader();
        assertThrows(
            IllegalArgumentException.class,
            () -> loader.loadFromFile(f.toString())
        );
    }
}