public class Robot extends BoardElement {
    private Position position;
    private final String name;

    public Robot(int row, int col, String name) {
        // Construct a new Position from row, col and assign it to this.position
        this.position = new Position(row, col);
        // Assign the name to this.name
        this.name = name;
    }

    public Position getPosition() {
        // return the position
        return this.position;
    }

    public String getName() {
        // return the name
        return this.name;
    }

    public void step(Direction direction) {
        // Single line: reassign this.position to its neighbor in `direction`.
        this.position = this.position.next(direction);
    }

    @Override
    public void renderOn(BoardDisplay display) {
        // Single line: call display.set with this robot's row, col, and name
        display.set(position.getR(), position.getC(), this.name);
    }

    @Override
    public boolean interact(Robot mover, Direction direction) {
        // Step 1: if mover is literally this same robot, return false.
        if (mover == this) return false;

        // Step 2: compute where `mover` would land after one step in `direction`.
        Position nextPos = mover.getPosition().next(direction);

        // Step 3 & 4: if that next-position equals this robot's position, return true; else false.
        return nextPos.equals(this.position);
    }
}
