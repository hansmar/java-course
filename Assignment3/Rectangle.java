public class Rectangle {

    // The upper-left corner of the rectangle
    private Point upperLeft;

    // The lower-right corner of the rectangle
    private Point lowerRight;

    // The color of the rectangle
    private Color color;

    // Constructs a Rectangle with two corner points and a color.
    public Rectangle(Point p1, Point p2, Color color) {
        this.upperLeft = p1;
        this.lowerRight = p2;
        this.color = color;
    }

    /*
     * Checks whether a given point lies inside or on the boundary
     * of the rectangle.
    */
    public boolean isInside(Point p) {
        return p.getX() >= upperLeft.getX() && 
               p.getX() <= lowerRight.getX() && 
               p.getY() >= upperLeft.getY() && 
               p.getY() <= lowerRight.getY();
    }

    // Returns the color of the rectangle.
    public Color getColor() {
        return color;
    }
}