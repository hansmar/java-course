// Canvas class handling the grid and buffered printing
class Canvas {
    private final Cell[][] grid;
    private final int width;
    private final int height;

    public Canvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Cell(' ', Color.WHITE, Color.BLACK);
            }
        }
    }

    // Out-of-bounds inputs are silently ignored to protect rendering performance and avoid terminal log flooding.
    public void set(int x, int y, char character, Color fg, Color bg) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x].setCharacter(character);
            grid[y][x].setFgColor(fg);
            grid[y][x].setBgColor(bg);
        }
    }

    public void show() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = grid[y][x];
                sb.append(cell.getBgColor().getBg())
                  .append(cell.getFgColor().getFg())
                  .append(cell.getCharacter())
                  .append(Color.RESET);
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());
    }
}