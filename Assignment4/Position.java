public class Position {
    private final int r;
    private final int c;

    public Position(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public Position next(Direction d) {

        // Return a new instance to keep Position immutable
        return new Position(this.r + d.getDr(), this.c + d.getDc());
    }
}