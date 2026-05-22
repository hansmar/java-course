public class Cell {
    private char character;
    private Color fgColor;
    private Color bgColor;

    public Cell(char character, Color fgColor, Color bgColor) {
        this.character = character;
        this.fgColor = fgColor;
        this.bgColor = bgColor;
    }

    public char getCharacter() { return character; }
    public void setCharacter(char character) { this.character = character; }

    public Color getFgColor() { return fgColor; }
    public void setFgColor(Color fgColor) { this.fgColor = fgColor; }

    public Color getBgColor() { return bgColor; }
    public void setBgColor(Color bgColor) { this.bgColor = bgColor; }
}