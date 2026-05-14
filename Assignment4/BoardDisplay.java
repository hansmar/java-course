public class BoardDisplay {
    private String[][] content;
    private boolean[][] southWall;
    private boolean[][] eastWall;
    private int rows, cols;

    private Color[][] cellColor;

    public BoardDisplay(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        
        content = new String[rows][cols];
        southWall = new boolean[rows][cols];
        eastWall = new boolean[rows][cols];
        cellColor = new Color[rows][cols];

        // Initialize with spaces to prevent NullPointerException during show()
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                content[r][c] = "  ";
                cellColor[r][c] = Color.DEFAULT;
            }
        }
    }

    public void set(int row, int col, String text) {
        set(row, col, text, Color.DEFAULT);
    }

    public void set(int row, int col, String text, Color color) {
        int r = row - 1;
        int c = col - 1;

        if (text == null) {
            content[r][c] = "  ";
        } else if (text.length() < 2) {
            content[r][c] = (text + "  ").substring(0, 2);
        } else {
            content[r][c] = text.substring(0, 2);
        }
        cellColor[r][c] = color;
    }

    public void setSouthWall(int row, int col) {
        southWall[row - 1][col - 1] = true;
    }

    public void setEastWall(int row, int col) {
        eastWall[row - 1][col - 1] = true;
    }

        public void show() {
        // 1. Top Boundary
        printHorizontalBoundary();

        for (int r = 0; r < rows; r++) {
            // 2. Content Row
            printContentRow(r);

            // 3. South-Walls Row (or Bottom Boundary for the last row)
            if (r == rows - 1) {
                printHorizontalBoundary();
            } else {
                printSouthWallRow(r);
            }
        }
    }

    private void printHorizontalBoundary() {
        StringBuilder sb = new StringBuilder("+");
        for (int c = 0; c < cols; c++) {
            sb.append("--+");
        }
        System.out.println(sb.toString());
    }

    private void printContentRow(int r) {
        StringBuilder sb = new StringBuilder("|");
        for (int c = 0; c < cols; c++) {
            // NEW: wrap content with color codes
            sb.append(cellColor[r][c].getCode());
            sb.append(content[r][c]);
            sb.append(Color.DEFAULT.getCode());

            if (eastWall[r][c] || c == cols - 1) {
                sb.append("|");
            } else {
                sb.append(" ");
            }
        }
        System.out.println(sb);
    }

    private void printSouthWallRow(int r) {
        StringBuilder sb = new StringBuilder("+");
        for (int c = 0; c < cols; c++) {
            if (southWall[r][c]) {
                sb.append("--+");
            } else {
                sb.append("  +");
            }
        }
        System.out.println(sb.toString());
    }

}
