import java.util.List;

public class Goal extends BoardElement {
    private final Position position;
    private final String name;

    public Goal(int row, int col, String name) {
        // Same pattern as Robot's constructor:
        // build a Position from row/col and store the name.
        this.position = new Position(row, col);
        this.name = name;
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // Same idea as Robot: display.set(row, col, name).
        display.set(position.getR(), position.getC(), name);
    }

    @Override
    public boolean interact(Robot robot, Direction direction) {
        return false;
    }

    @Override
    public boolean gameOver(List<Robot> robots) {
        // Walk over `robots`. If any robot's position equals this goal's
        // position, the game is over — return true.
        for (Robot robot : robots) {
            if (robot.getPosition().equals(this.position)) {
                return true;
            }
        }
        return false;
    }
}
