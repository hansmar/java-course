import java.util.List;
import java.util.Scanner;

public class Game {
    private final List<Dungeon> dungeons;
    private int currentLevel;
    private final Player player;
    private final Scanner scanner;
    private boolean isRunning;
    private String statusMessage;

    public Game(List<Dungeon> dungeons, Player player, Scanner scanner) {
        if (dungeons == null || dungeons.isEmpty()) {
            throw new IllegalArgumentException("Game requires at least one dungeon.");
        }
        this.dungeons = dungeons;
        this.currentLevel = 0;
        this.player = player;
        this.scanner = scanner;
        this.isRunning = true;
        this.statusMessage = "Welcome to the Dungeon! W/A/S/D to move, U to use first item, Q to quit.";
    }

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

    public boolean endedNaturally() {
        return player.isDead() || player.hasWon();
    }

    private void tick() {
        // 1. Clear Console Screen Buffer
        clearScreen();

        // 2. Build the visual frame
        Canvas canvas = new Canvas(currentDungeon().getWidth(), currentDungeon().getHeight());
        currentDungeon().renderOn(canvas);
        player.renderOn(canvas);
        canvas.show();

        // 3. Status Display
        String fuseInfo = bombFuseInfo();
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth()
                + "   Inventory: " + inventoryDisplay()
                + fuseInfo);
        System.out.println("Status: " + statusMessage);
        System.out.print("> ");

        // 4. Capture Input
        String input = scanner.nextLine().trim().toLowerCase();

        boolean playerActed = false;

        if (input.isEmpty()) {
            statusMessage = "(waiting...)";
        } else {
            char command = input.charAt(0);

            if (command == 'q') {
                isRunning = false;
                return;
            }

            if (command == 'u') {
                String result = player.useItem(0);
                if (result == null) {
                    statusMessage = "Nothing to use.";
                    // Doesn't count as a turn — falls through to !playerActed path.
                } else {
                    statusMessage = result;
                    playerActed = true;
                }

            } else {
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
                        statusMessage = "Unknown command. Use W, A, S, D, U, or Q.";
                        isMoveCommand = false;
                    }
                }

                if (isMoveCommand) {
                    playerActed = true;
                    // Reset status message for the new turn
                    statusMessage = "";

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
            } // close the else { /* movement */ } that wraps move handling
        }

        // 6. Level transition: if the player stepped on a staircase, advance.
        if (player.wantsNextLevel()) {
            player.clearNextLevelRequest();
            if (currentLevel + 1 < dungeons.size()) {
                currentLevel++;
                Dungeon next = currentDungeon();
                player.moveTo(next.getStartX(), next.getStartY());
                // (e.g. "You descend the staircase...") deserves to survive.
                String prefix = statusMessage.isEmpty() ? "" : statusMessage + " ";
                statusMessage = prefix + "You arrive on level " + (currentLevel + 1) + ".";
            } else {
                statusMessage = "The staircase leads nowhere... (no further levels)";
            }
        }

        // 7. End of turn: enemies act ONLY if the player took a real turn.
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

        // 8. Bomb placement.
        LitBomb pending = player.takePendingBomb();
        if (pending != null) {
            currentDungeon().addEntity(pending);
        }

        currentDungeon().cleanup();
    }

    private void clearScreen() {
        // ANSI escape sequence to clear screen and move cursor home
        System.out.print("\u001b[H\u001b[2J");
        System.out.flush();
    }

    // Comma-separated inventory listing for the status display.
    private String inventoryDisplay() {
        List<Item> inv = player.getInventory();
        if (inv.isEmpty()) return "(empty)";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inv.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(inv.get(i).getName());
        }
        return sb.toString();
    }

    // Surface the most urgent (shortest-fuse) bomb's countdown in the HUD.
    private String bombFuseInfo() {
        int minFuse = Integer.MAX_VALUE;
        for (Entity e : currentDungeon().getEntitiesSnapshot()) {
            if (e instanceof LitBomb bomb && bomb.getFuseTurnsRemaining() < minFuse) {
                minFuse = bomb.getFuseTurnsRemaining();
            }
        }
        if (minFuse == Integer.MAX_VALUE) return "";
        return "   Bomb fuse: " + minFuse;
    }
}