public class Assignment3 {
    public static void main(String[] args) {
        Grid grid = new Grid(40, 40);
        Rectangle rectangle = new Rectangle(
            new Point(13, 5),
            new Point(23, 27),
            new Color(255, 0, 0));
        Circle circle = new Circle(
            new Point(25, 25),
            10,
            new Color(0, 255, 0));
        grid.drawCircle(circle);
        grid.drawRectangle(rectangle);
        grid.renderGrid();
    }
}