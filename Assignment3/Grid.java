public class Grid {

    private int h;
    private int w;
    private GridCell[][] cells;
    
    public Grid(int h, int w) {
        this.h = h;
        this.w = w;
        this.cells = new GridCell[h][w];
    
        Color white = new Color(255, 255, 255);
    
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                cells[y][x] = new GridCell(white);
            }
        }
    }

    public void drawRectangle(Rectangle rectangle) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Point p = new Point(y, x);
                if (rectangle.isInside(p)) {
                    Color current = cells[y][x].getColor();
                    Color newColor = Color.mixColors(current, rectangle.getColor());
                    cells[y][x] = new GridCell(newColor);
                }
            }
        }
    }


    public void drawCircle(Circle circle) {
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Point p = new Point(y, x);
                if (circle.isInside(p)) {
                    Color current = cells[y][x].getColor();
                    Color newColor = Color.mixColors(current, circle.getColor());
                    cells[y][x] = new GridCell(newColor);
                
    
                cells[y][x] = new GridCell(newColor);
                }
            }
        }
    }

    public void renderGrid() {
        ClearConsole();
    
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                cells[y][x].printGridCell();
            }
            System.out.println();
        }
    }

    private void ClearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception ex) {}
    }
}