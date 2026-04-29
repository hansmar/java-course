public class Circle {

    // The center point of the circle
    private Point center;

    // The radius of the circle (must be non-negative)
    private int radius;

    // The color of the circle
    private Color color;

    // Constructs a Circle with a given center, radius, and color.

    public Circle(Point center, int radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Checks whether a given point lies inside or on the boundary
     * of the circle.
     * Uses the distance formula:
     * (x - centerX)^2 + (y - centerY)^2 <= radius^2
     */
    public boolean isInside(Point p) {

        // Difference in x and y coordinates
        int dx = p.getX() - center.getX();
        int dy = p.getY() - center.getY();

        // Compare squared distance to squared radius (avoids using sqrt)
        return dx * dx + dy * dy <= radius * radius;
    }

    // Returns the color of the circle.
    public Color getColor() {
        return this.color;
    }
}