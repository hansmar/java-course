public class BoardFrame extends BoardElement {
    private final int rows;
    private final int cols;

    public BoardFrame(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // Empty body. BoardDisplay's show() always draws the outer boundary;
        // BoardFrame doesn't need to do anything visible.
    }

    @Override
    public boolean interact(Robot robot, Direction direction) {
        // Compute where the robot would land if it stepped one cell in `direction`.
        Position nextPos = robot.getPosition().next(direction);

        int r = nextPos.getR();
        int c = nextPos.getC();

        // If that next position falls outside the board, return true (block).
        // Valid range is 1 to rows (inclusive) and 1 to cols (inclusive).
        if (r < 1 || r > this.rows || c < 1 || c > this.cols) {
            return true;
        }

        // Otherwise return false.
        return false;
    }
}
