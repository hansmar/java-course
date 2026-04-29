import java.util.ArrayList;

public class ParagraphAnalyzer {
   private String text;
   private String[] paragraphs;

   public ParagraphAnalyzer(String text) {
      this.text = text;
   }

   // Performs the paragraph-level analysis:
   // splits the text on blank lines and discards empty pieces.
   public void analyze() {
      // Split on one or more blank lines (handles both \n\n and \r\n\r\n).
      String[] parts = text.split("\\r?\\n\\s*\\r?\\n");

      ArrayList<String> list = new ArrayList<String>();
      for (int i = 0; i < parts.length; i++) {
         String trimmed = parts[i].trim();
         if (trimmed.length() > 0) {
            list.add(trimmed);
         }
      }

      // Copy the ArrayList into a plain String[] so the next analyzer
      // can work with a simple array.
      paragraphs = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
         paragraphs[i] = list.get(i);
      }
   }

   public String[] getParagraphs() {
      return paragraphs;
   }

   public int getParagraphCount() {
      return paragraphs.length;
   }
}
