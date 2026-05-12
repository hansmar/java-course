public class VerticalWall extends BoardElement {
    private final int col;
    private final int minRow;
    private final int maxRow;

    public VerticalWall(int row, int col, int n) {
        this.col = col;
        // Convert signed n into a row range.
        int endRow = row + (n > 0 ? n - 1 : n + 1);
        this.minRow = Math.min(row, endRow);
        this.maxRow = Math.max(row, endRow);
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // For each row from minRow to maxRow inclusive,
        // call display.setEastWall(r, col).
        for (int r = minRow; r <= maxRow; r++) {
            display.setEastWall(r, col);
        }
    }

    @Override
    public boolean interact(Robot robot, Direction direction) {
        // Grab the robot's current row and column.
        int r = robot.getPosition().getR();
        int c = robot.getPosition().getC();

        // Check if the robot's row is within the vertical span of this wall.
        boolean rowInRange = (r >= minRow && r <= maxRow);

        if (rowInRange) {
            // Case 1: Robot is in column `col` and tries to move EAST.
            if (direction == Direction.EAST && c == col) {
                return true;
            }
            // Case 2: Robot is in column `col + 1` and tries to move WEST.
            if (direction == Direction.WEST && c == col + 1) {
                return true;
            }
        }
        return false;
    }
}
