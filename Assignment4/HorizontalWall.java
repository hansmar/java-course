public class HorizontalWall extends BoardElement {
    private final int row;
    private final int minCol;
    private final int maxCol;

    public HorizontalWall(int row, int col, int n) {
        this.row = row;
        // Convert signed n into a column range.
        int endCol = col + (n > 0 ? n - 1 : n + 1);
        this.minCol = Math.min(col, endCol);
        this.maxCol = Math.max(col, endCol);
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // For each column from minCol to maxCol inclusive,
        // call display.setSouthWall(row, c).
        for (int c = minCol; c <= maxCol; c++) {
            display.setSouthWall(row, c);
        }
    }

    @Override
    public boolean interact(Robot robot, Direction direction) {
        // The wall sits between row `row` and row `row + 1`,
        // across the column range [minCol, maxCol].
        int r = robot.getPosition().getR();
        int c = robot.getPosition().getC();

        // Check if the robot's column is within the horizontal span of this wall.
        boolean colInRange = (c >= minCol && c <= maxCol);

        if (colInRange) {
            // Case 1: Robot is in row `row` and tries to move SOUTH.
            if (direction == Direction.SOUTH && r == row) {
                return true;
            }
            // Case 2: Robot is in row `row + 1` and tries to move NORTH.
            if (direction == Direction.NORTH && r == row + 1) {
                return true;
            }
        }

        // Any other move passes through.
        return false;
    }
}
