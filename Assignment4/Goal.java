import java.util.List;

public class Goal extends BoardElement {
    private final Position position;
    private final String name;
    private final Color color;

    public Goal(int row, int col, String name, Color color) {
        // Same pattern as Robot's constructor:
        // build a Position from row/col and store the name.
        this.position = new Position(row, col);
        this.name = name;
        this.color = color;
    }

    @Override
    public void renderOn(BoardDisplay display) {
        display.set(position.getR(), position.getC(), this.name, this.color);
    }

    @Override
    public boolean interact(Robot robot, Direction direction) {
        // Goals don't block — robots slide right through them.
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
