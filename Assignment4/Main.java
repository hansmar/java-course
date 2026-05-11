public class Main {
    public static void main(String[] args) {
        Position p = new Position(2, 3);
        Position p2 = p.next(Direction.NORTH);

        System.out.print(p2.getR() + ", " + p2.getC());
    }
}