public class Robot extends BoardElement {
    private Position position;
    private final String name;
    private final Color color;

    public Robot(int row, int col, String name, Color color) {
        // Construct a new Position from row, col and assign it to this.position
        this.position = new Position(row, col);
        this.name = name;
        this.color = color;
    }

    public Position getPosition() {
        return this.position;
    }

    public String getName() {
        return this.name;
    }

    public void step(Direction direction) {
        this.position = this.position.next(direction);
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // Call display.set with this robot's row, col, and name
        display.set(position.getR(), position.getC(), this.name, this.color);
    }

    @Override
    public boolean interact(Robot mover, Direction direction) {
        // If mover is literally this same robot, return false.
        if (mover == this) return false;

        // Compute where `mover` would land after one step in `direction`.
        Position nextPos = mover.getPosition().next(direction);

        // If that next-position equals this robot's position, return true; else false.
        return nextPos.equals(this.position);
    }
}
