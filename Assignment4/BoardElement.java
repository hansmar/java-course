import java.util.List;

public abstract class BoardElement {
    // No position field here. Why? Because not every element has a single
    // position — walls span multiple cells, BoardFrame has none. Each
    // concrete subclass manages its own positional state.

    public abstract void renderOn(BoardDisplay display);

    // True if this element blocks the given robot from moving in the given direction.
    public abstract boolean interact(Robot robot, Direction direction);

    // Default: this element doesn't end the game. Goal will override.
    public boolean gameOver(List<Robot> robots) {
        return false;
    }
}