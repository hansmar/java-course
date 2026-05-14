public enum Color {
    RED("\033[31m"),
    GREEN("\033[32m"),
    YELLOW("\033[33m"),
    BLUE("\033[34m"),
    MAGENTA("\033[35m"),
    CYAN("\033[36m"),
    WHITE("\033[37m"),
    DEFAULT("\033[0m");

    private final String code;
    Color(String code) { this.code = code; }
    public String getCode() { return code; }
}