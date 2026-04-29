import java.util.ArrayList;

public class SentenceAnalyzer {
   private String[] paragraphs;
   private String[] sentences;

   public SentenceAnalyzer(String[] paragraphs) {
      this.paragraphs = paragraphs;
   }

   public void analyze() {
      ArrayList<String> list = new ArrayList<String>();

      // Walk through each paragraph and split on sentence-ending punctuation.
      for (int i = 0; i < paragraphs.length; i++) {
         String[] parts = paragraphs[i].split("[.!?]+");

         for (int j = 0; j < parts.length; j++) {
            String sentence = parts[j].trim();
            if (sentence.length() > 0) {
               list.add(sentence);
            }
         }
      }

      sentences = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
         sentences[i] = list.get(i);
      }
   }

   public String[] getSentences() {
      return sentences;
   }

   public int getSentenceCount() {
      return sentences.length;
   }
}
