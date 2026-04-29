public class Color {

    // ANSI escape code to reset all colors and styles in the console
    public static final String ANSI_RESET = "\u001B[0m";

    // Red, green, and blue components.
    public int r, g, b;

    // Constructs a Color with given RGB values.
    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    // Mixes two colors by averaging their RGB components.
    public static Color mixColors(Color c1, Color c2) {
        int avgR = (c1.r + c2.r) / 2;
        int avgG = (c1.g + c2.g) / 2;
        int avgB = (c1.b + c2.b) / 2;

        return new Color(avgR, avgG, avgB);
    }

    
    /** 
    * Generates an ANSI escape code string for setting background and foreground colors
    *  in a terminal that supports 24-bit (true color) ANSI codes.
    */
     public static String colorCode(Color bg, Color fg) {
        return String.format("\033[48;2;%d;%d;%d;38;2;%d;%d;%dm", 
                             bg.r, bg.g, bg.b, 
                             fg.r, fg.g, fg.b);
    }
}
