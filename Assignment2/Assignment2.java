public class Assignment2 {
    public static void main(String[] args) {
        TextReader reader = new TextReader();
        String text = reader.ReadFile();

        TextAnalyzer analyzer = new TextAnalyzer(text);
        analyzer.analyze();
    }
}