public class Main {
    public static void main(String[] args) {
        int width = 20;
        int height = 10;
        Canvas canvas = new Canvas(width, height);

        // Draw yellow '#' walls around the edges
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
                    canvas.set(x, y, '#', Color.YELLOW, Color.BLACK);
                }
            }
        }

        // Initialize player in the middle (x=10, y=5) with 100 HP
        Player player = new Player(10, 5, 100);

        // Let the player render itself onto the canvas
        player.renderOn(canvas);

        // Render everything to terminal
        canvas.show();
    }
}