public class Point {
    private int x;
    private int y;

    // First arg is row (y), second is column (x)
    public Point(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}