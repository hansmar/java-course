import java.util.Scanner;

public class Game {
    private final Dungeon dungeon;
    private final Player player;
    private final Scanner scanner;
    private boolean isRunning;
    private String statusMessage;

    public Game(Dungeon dungeon, Player player) {
        this.dungeon = dungeon;
        this.player = player;
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        this.statusMessage = "Welcome to the Dungeon! Use W/A/S/D to move. Q to quit.";
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
        Canvas canvas = new Canvas(dungeon.getWidth(), dungeon.getHeight());
        dungeon.renderOn(canvas);
        player.renderOn(canvas); // Render player last so they layer on top of floors/hazards
        canvas.show();

        // 3. Status Display
        System.out.println("HP: " + player.getHealth() + "/" + player.getMaxHealth()
                + "   Inventory: " + player.getInventory().size());
        System.out.println("Status: " + statusMessage);
        System.out.print("> ");

        // 4. Capture Input
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.isEmpty()) return;
        char command = input.charAt(0);

        if (command == 'q') {
            isRunning = false;
            return;
        }

        // 5. Evaluate Intended Movement Vector
        int targetX = player.getX();
        int targetY = player.getY();

        switch (command) {
            case 'w' -> targetY--;
            case 's' -> targetY++;
            case 'a' -> targetX--;
            case 'd' -> targetX++;
            default -> {
                statusMessage = "Unknown command. Use W, A, S, D, or Q.";
                return;
            }
        }

        // 6. Handle Interaction and Boundaries
        statusMessage = ""; // Reset status message for the new turn
        
        // Canvas boundary validation
        if (targetX < 0 || targetX >= dungeon.getWidth() || targetY < 0 || targetY >= dungeon.getHeight()) {
            statusMessage = "An unseen magical force blocks you from leaving the bounds.";
            return;
        }

        Entity targetEntity = dungeon.entityAt(targetX, targetY);
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

        // 7. End of turn engine updates
        dungeon.cleanup();
    }

    private void clearScreen() {
        // ANSI escape sequence to clear screen and move cursor home
        System.out.print("\u001b[H\u001b[2J");
        System.out.flush();
    }
}