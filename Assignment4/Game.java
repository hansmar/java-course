import java.util.Scanner;

public class Game {
    private final Board board;
    private int moveCount = 0;
    private final Scanner scanner = new Scanner(System.in);

    public Game() {
        // Build the board for the configuration:
        this.board = new Board(4, 7);
        
        // Add Frame
        board.addElement(new BoardFrame(4, 7));
        
        // Add Robots
        board.addRobot(new Robot(1, 1, "BB"));
        board.addRobot(new Robot(2, 3, "AA"));
        board.addRobot(new Robot(4, 7, "CC"));
        
        // Add Goal
        board.addElement(new Goal(3, 6, "gg"));
        
        // Add Walls
        board.addElement(new VerticalWall(2, 3, 1));   // east wall right of AA
        board.addElement(new HorizontalWall(1, 4, 2)); // south walls under (1,4) and (1,5)
        board.addElement(new HorizontalWall(2, 3, 1)); // south wall under AA
    }

    public void play() {
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
                scanner.nextLine(); scanner.nextLine(); // Pause
                continue;
            }

            System.out.print("Direction (n/s/e/w): ");
            String dirInput = scanner.next();
            Direction dir = parseDirection(dirInput);
            if (dir == null) {
                System.out.println("Invalid direction! Press enter to try again.");
                scanner.nextLine(); scanner.nextLine(); // Pause
                continue;
            }

            board.move(robot, dir);
            moveCount++;
        }

        // Final Win State
        clearConsole();
        board.render().show();
        System.out.println("You won in " + moveCount + " moves!");
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
