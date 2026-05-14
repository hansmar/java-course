import java.util.List;

public abstract class BoardElement {

    public abstract void renderOn(BoardDisplay display);

    public void tick() {
    // Default: most elements have no time-dependent state.
    }

    public abstract boolean interact(Robot robot, Direction direction);

    // Default: this element doesn't end the game. Goal overrides.
    public boolean gameOver(List<Robot> robots) {
        return false;
    }
}