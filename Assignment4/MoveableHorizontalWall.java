import java.util.Random;

public class MoveableHorizontalWall extends BoardElement {
    private int row;
    private int minCol, maxCol;
    private final int length;
    private final int minRow, maxRow;
    private final int colBoundMin, colBoundMax;
    private final int movesPerStep;
    private int tickCount = 0;
    private final Random rng = new Random();

    public MoveableHorizontalWall(int row, int col, int n,
                                  int minRow, int maxRow,
                                  int colBoundMin, int colBoundMax,
                                  int movesPerStep) {
        this.row = row;
        int endCol = col + (n > 0 ? n - 1 : n + 1);
        this.minCol = Math.min(col, endCol);
        this.maxCol = Math.max(col, endCol);
        this.minRow = minRow;
        this.maxRow = maxRow;
        this.colBoundMin = colBoundMin;
        this.colBoundMax = colBoundMax;
        this.movesPerStep = movesPerStep;
        this.length = this.colBoundMax - this.minCol +1;
    }

    @Override
    public void tick() {
        tickCount++;
        if (tickCount % movesPerStep == 0) {
            row = minRow + new Random().nextInt(maxRow - minRow + 1);
            int startCol = colBoundMin + rng.nextInt(colBoundMax - colBoundMin - length + 2);
            minCol = startCol;
            maxCol = startCol + length -1;
        }
    }

    @Override
    public void renderOn(BoardDisplay display) {
        for (int c = minCol; c <= maxCol; c++) {
            display.setSouthWall(row, c);
        }
    }

    @Override
    public boolean interact(Robot robot, Direction d) {
        int r = robot.getPosition().getR();
        int c = robot.getPosition().getC();
        boolean colInRange = (c >= minCol && c <= maxCol);
        if (colInRange) {
            if (d == Direction.SOUTH && r == row) return true;
            if (d == Direction.NORTH && r == row + 1) return true;
        }
        return false;
    }
}