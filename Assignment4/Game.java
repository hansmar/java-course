import java.util.Scanner;

public class Game {
    private Board board;
    private int moveCount = 0;
    private final Scanner scanner = new Scanner(System.in);

    private void setupBoard() {
        this.board = new Board(4, 7);
        board.addElement(new BoardFrame(4, 7));
        board.addElement(new Goal(3, 6, "gg", Color.RED));
        board.addRobot(new Robot(1, 1, "BB", Color.BLUE));
        board.addRobot(new Robot(2, 3, "AA", Color.GREEN));
        board.addRobot(new Robot(4, 7, "CC", Color.YELLOW));
        board.addElement(new VerticalWall(2, 3, 1));
        board.addElement(new HorizontalWall(1, 4, 2));
        board.addElement(new HorizontalWall(2, 3, 1));
        board.addElement(new MoveableHorizontalWall(3, 1, 2, 1, 3, 1, 7, 1));
    }

    public Game() {
        setupBoard();
    }

    public void play() {
    while (true) {
        // Inner loop: one round
        while (!isGameOver()) {
            clearConsole();
            board.render().show();

            System.out.println("Moves: " + moveCount);
            System.out.print("Robots: ");
            for (Robot r : board.robots()) { System.out.print(r.getName() + " "); }
            System.out.println();

            System.out.print("Select a robot: ");
            String robotName = scanner.next();
            Robot robot = findRobot(robotName);
            if (robot == null) {
                System.out.println("No such robot! Press enter to try again.");
                scanner.nextLine(); scanner.nextLine();
                continue;
            }

            System.out.print("Direction (n/s/e/w): ");
            String dirInput = scanner.next();
            Direction dir = parseDirection(dirInput);
            if (dir == null) {
                System.out.println("Invalid direction! Press enter to try again.");
                scanner.nextLine(); scanner.nextLine();
                continue;
            }

            board.move(robot, dir);
            moveCount++;
            board.tick();
        }

        // Round ended — show win screen
        clearConsole();
        board.render().show();
        System.out.println("You won in " + moveCount + " moves!");

        // Retry prompt
        System.out.print("Play again? (y/n): ");
        String again = scanner.next().trim().toLowerCase();
        if (!again.startsWith("y")) {
            System.out.println("Thanks for playing!");
            break;
        }

        // Reset state for a fresh round
        moveCount = 0;
        setupBoard();
        }
    }

    private boolean isGameOver() {
        for (BoardElement element : board.elements()) {
            if (element.gameOver(board.robots())) {
                return true;
            }
        }
        return false;
    }

    private Robot findRobot(String name) {
        for (Robot r : board.robots()) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    private Direction parseDirection(String input) {
        input = input.toLowerCase();
        if (input.startsWith("n")) return Direction.NORTH;
        if (input.startsWith("s")) return Direction.SOUTH;
        if (input.startsWith("e")) return Direction.EAST;
        if (input.startsWith("w")) return Direction.WEST;
        return null;
    }

    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
