import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows;
    private final int cols;
    private final List<BoardElement> elements = new ArrayList<>();
    private final List<Robot> robots = new ArrayList<>();

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void tick() {
        for (BoardElement e : elements) {
            e.tick();
        }
    }

    public void addRobot(Robot robot) {
        // Add to BOTH lists — robots is a subset of elements.
        robots.add(robot);
        elements.add(robot);
    }

    public void addElement(BoardElement element) {
        elements.add(element);
    }

    public List<BoardElement> elements() {
        return elements;
    }

    public List<Robot> robots() {
        return robots;
    }

    /**
     * The slide algorithm: keep stepping the robot until something blocks.
     */
    public void move(Robot robot, Direction direction) {
        while (!isBlocked(robot, direction)) {
            robot.step(direction);
        }
    }

    /**
     * Helper method to check if any element on the board blocks the movement.
     */
    private boolean isBlocked(Robot robot, Direction direction) {
        for (BoardElement element : elements) {
            if (element.interact(robot, direction)) {
                return true; // Something blocked the path
            }
        }
        return false;
    }

    public BoardDisplay render() {
        BoardDisplay display = new BoardDisplay(this.rows, this.cols);
        
        // Have every element render itself onto the display
        for (BoardElement element : elements) {
            element.renderOn(display);
        }
        
        return display;
    }
}
