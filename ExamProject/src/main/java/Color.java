public enum Color {
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    MAGENTA("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m"); // Removed RESET from enum list

    // Global control code constant
    public static final String RESET = "\u001B[0m";

    private final String fgCode;
    private final String bgCode;

    Color(String fgCode, String bgCode) {
        this.fgCode = fgCode;
        this.bgCode = bgCode;
    }

    public String getFg() { return fgCode; }
    public String getBg() { return bgCode; }
}