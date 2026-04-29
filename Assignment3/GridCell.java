public class GridCell {
    // Two private fields
    private Color color;
    private String content;

    public GridCell(Color color) {
        this.color = color;
        this.content = "   ";
    }
    
    public void printGridCell() {
        System.out.print(Color.colorCode(this.color, this.color) + content + Color.ANSI_RESET);
    }
    
    public Color getColor() {
        return this.color;
    }
}
