# Roguelike

A small console-based roguelike written in Java for the OOP with Java 1 exam project.
The player moves through a multi-level dungeon, fights monsters, picks up items, avoids
traps, and wins by reaching the treasure on the final level.

## Running the game

Requires Java 17 or newer.

Compile and run from the project root:

```bash
javac -d out src/*.java
java -cp out Main
```

The level files in `levels/` are loaded relative to the working directory, so run
the game from the project root rather than from inside `out/`.

## Controls

| Key | Action                            |
|-----|-----------------------------------|
| `w` | Move up                           |
| `a` | Move left                         |
| `s` | Move down                         |
| `d` | Move right                        |
| `u` | Use the first item in inventory   |
| `q` | Quit                              |

Each command must be followed by Enter — input is line-buffered through `Scanner`.

After the player dies or reaches the treasure, the game offers a restart prompt.
Pressing `r` reloads the level files from disk and starts over from level 1; any
other key exits.

## What the symbols mean

| Symbol | Entity                                                                 |
|--------|------------------------------------------------------------------------|
| `@`    | Player                                                                 |
| `#`    | Indestructible wall                                                    |
| `%`    | Destructible wall (40 HP, breakable with attacks)                      |
| `.`    | Floor                                                                  |
| `~`    | Water (heals 25 HP when stepped on)                                    |
| `^`    | Fire (10 damage per step, extinguishes after 3 interactions)           |
| `T`    | Floor trap (15 damage, one-shot)                                       |
| `+`    | Arrow trap (20 damage, fires on line of sight, one-shot)               |
| `Z`    | Zombie (40 HP, 8 damage, 1 step per turn)                              |
| `G`    | Ghoul (25 HP, 12 damage, 2 steps per turn)                             |
| `d`    | Cave dog (20 HP, 5 damage, 2 steps per turn)                           |
| `s`    | Slime (60 HP, 4 damage, acts every other turn)                         |
| `P`    | Health potion pickup (+30 HP when used)                                |
| `B`    | Bomb pickup (30 damage, 3-turn fuse, 3x3 blast radius)                 |
| `>`    | Staircase to the next level                                            |
| `$`    | Treasure (win condition)                                               |

## Level format

Levels are plain text files in `levels/`. Each character maps to one entity. The
loader expects the first `@` to mark the player start; subsequent `@` characters
are treated as floor. Lines may be ragged — shorter rows are padded with floor.
Unknown characters produce an `IllegalArgumentException` with the offending
line and column, so typos in level files fail loudly during development.

To add a new level, drop a `.txt` file into `levels/` and add it to the
`LEVEL_FILES` array in `Main.java`. Levels load in order; the last level should
contain the treasure and no staircase.

## Project structure

```
src/         Java source files
levels/      Level definition files
tests/       JUnit 5 test files
README.md
Java_OOP_1___Exam_Project.pdf
```

## Tests

The project includes 43 unit tests across seven files, covering the player,
entity interactions, enemy combat, the dungeon, the level loader, the
destructible wall, and the lit bomb. All tests are black-box: they verify
behaviour through public APIs without touching internals.

Tests require JUnit 5. With `junit-platform-console-standalone.jar` in `lib/`:

```bash
javac -cp lib/junit-platform-console-standalone-*.jar -d out src/*.java tests/*.java
java -jar lib/junit-platform-console-standalone-*.jar \
     --class-path out --scan-class-path
```

## Author

Hansmar Poulsen (Group 03), May 2026.