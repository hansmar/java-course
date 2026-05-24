import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Dungeon> dungeons;
    private int currentLevel;
    private final Player player;
    private final Scanner scanner;
    private boolean isRunning;
    private String statusMessage;

    public Game(List<Dungeon> dungeons, Player player) {
        if (dungeons == null || dungeons.isEmpty()) {
            throw new IllegalArgumentException("Game requires at least one dungeon.");
        }
        this.dungeons = dungeons;
        this.currentLevel = 0;
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.statusMessage = "Welcome to the Dungeon! Use W/A/S/D to move. Q to quit.";
    }

    // Single source of truth for "which level are we on right now". Every place
    // that used to say `dungeon` goes through here, so the level-advance logic
    // only lives in one spot.
    private Dungeon currentDungeon() {
        return dungeons.get(currentLevel);
    }

    public void start() {
        while (isRunning && !player.isDead() && !player.hasWon()) {
            tick();
        }

        if (player.isDead()) {
            System.out.println("\n*** YOU DIED ***");
        } else if (player.hasWon()) {
            System.out.println("\n*** YOU FOUND THE TREASURE! YOU WIN! ***");
        } else {
            System.out.println("\nGoodbye!");
        }
    }

    private void tick() {
        // 1. Clear Console Screen Buffer
        clearScreen();

        // 2. Build the visual frame
        Canvas canvas = new Canvas(currentDungeon().getWidth(), currentDungeon().getHeight());
        currentDungeon().renderOn(canvas);
        player.renderOn(canvas); // Render player last so they layer on top of floors/hazards
        canvas.show();

        // 3. Status Display
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth()
                + "   Inventory: " + player.getInventory().size());
        System.out.println("Status: " + statusMessage);
        System.out.print("> ");

        // 4. Capture Input
        String input = scanner.nextLine().trim().toLowerCase();

        // Whether the player took a real turn this tick. Empty input, unknown
        // commands, and quit do NOT count as turns — enemies don't act either.
        // Rationale: a typo or accidental enter shouldn't get you killed. Movement
        // attempts DO count, even if they're blocked by a wall — otherwise the
        // player could exploit "walk into wall" as a free turn-skip.
        boolean playerActed = false;

        if (input.isEmpty()) {
            statusMessage = "(waiting...)";
        } else {
            char command = input.charAt(0);

            if (command == 'q') {
                isRunning = false;
                return;
            }

            // 5. Evaluate Intended Movement Vector
            int targetX = player.getX();
            int targetY = player.getY();
            boolean isMoveCommand = true;

            switch (command) {
                case 'w' -> targetY--;
                case 's' -> targetY++;
                case 'a' -> targetX--;
                case 'd' -> targetX++;
                default -> {
                    statusMessage = "Unknown command. Use W, A, S, D, or Q.";
                    isMoveCommand = false;
                }
            }

            if (isMoveCommand) {
                playerActed = true;
                statusMessage = ""; // Reset status message for the new turn

                // Canvas boundary validation
                if (targetX < 0 || targetX >= currentDungeon().getWidth() || targetY < 0 || targetY >= currentDungeon().getHeight()) {
                    statusMessage = "An unseen magical force blocks you from leaving the bounds.";
                } else {
                    Entity targetEntity = currentDungeon().entityAt(targetX, targetY);
                    boolean canEnter;

                    if (targetEntity == null) {
                        canEnter = true;
                    } else {
                        // Let the entity speak for itself first
                        String result = targetEntity.interactWith(player);

                        // Decide if the player can slide into the cell coordinate
                        canEnter = !targetEntity.isOccupying();

                        // Only inject the generic bump line if the entity had nothing to say.
                        // This keeps "entities own their voice" — Game doesn't impose flavor.
                        if (!canEnter && result.isEmpty()) {
                            result = "Ouch! You bumped into something solid.";
                        }
                        statusMessage = result;
                    }

                    if (canEnter) {
                        player.moveTo(targetX, targetY);
                    }
                }
            }
        }

        // 6. Level transition: if the player stepped on a staircase, advance.
        // Done BEFORE the enemy phase, so enemies on the new level don't get
        // a free swing on the transition step.
        if (player.wantsNextLevel()) {
            player.clearNextLevelRequest();
            if (currentLevel + 1 < dungeons.size()) {
                currentLevel++;
                Dungeon next = currentDungeon();
                player.moveTo(next.getStartX(), next.getStartY());
                // Accumulate rather than overwrite — the staircase's own message
                // (e.g. "You descend the staircase...") deserves to survive.
                String prefix = statusMessage.isEmpty() ? "" : statusMessage + " ";
                statusMessage = prefix + "You arrive on level " + (currentLevel + 1) + ".";
            } else {
                // Shouldn't happen — last level shouldn't contain a staircase.
                // Defensive: if it does, ignore the request rather than crash.
                statusMessage = "The staircase leads nowhere... (no further levels)";
            }
        }

        // 7. End of turn: enemies act ONLY if the player took a real turn.
        // Also skip if the player has already died or won this turn — no point
        // letting enemies attack a corpse or the engine bookkeeping a finished game.
        if (playerActed && !player.isDead() && !player.hasWon()) {
            List<String> turnMessages = currentDungeon().tickAll(player);
            if (!turnMessages.isEmpty()) {
                StringBuilder sb = new StringBuilder(statusMessage);
                for (String m : turnMessages) {
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(m);
                }
                statusMessage = sb.toString();
            }
        }
        currentDungeon().cleanup();
    }

    private void clearScreen() {
        // ANSI escape sequence to clear screen and move cursor home
        System.out.print("\u001b[H\u001b[2J");
        System.out.flush();
    }
}