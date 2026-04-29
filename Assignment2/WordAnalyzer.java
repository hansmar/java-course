import java.util.ArrayList;

public class WordAnalyzer {
   private String[] sentences;
   private String[] words;

   // words with more than 6 characters
   private int longWordCount;

   // sum of all word lengths
   private int totalLetters;

   public WordAnalyzer(String[] sentences) {
      this.sentences = sentences;
   }

   public void analyze() {
      ArrayList<String> list = new ArrayList<String>();
      longWordCount = 0;
      totalLetters = 0;

      for (int i = 0; i < sentences.length; i++) {
         // Split on any whitespace (spaces, tabs, newlines).
         String[] parts = sentences[i].split("\\s+");

         for (int j = 0; j < parts.length; j++) {
            String word = cleanWord(parts[j]);

            if (word.length() > 0) {
               list.add(word);
               totalLetters += word.length();
               if (word.length() > 6) {
                  longWordCount++;
               }
            }
         }
      }

      words = new String[list.size()];
      for (int i = 0; i < list.size(); i++) {
         words[i] = list.get(i);
      }
   }

   // Strips leading and trailing punctuation so things like
   // "duckling," or "(Hans)" count as plain words.
   private String cleanWord(String raw) {
      String result = "";
      for (int i = 0; i < raw.length(); i++) {
         char c = raw.charAt(i);
         // Keep letters, digits and a couple of in-word characters.
         if (Character.isLetterOrDigit(c) || c == '\'' || c == '-') {
            result = result + c;
         }
      }
      return result;
   }

   public String[] getWords() {
      return words;
   }

   public int getWordCount() {
      return words.length;
   }

   public int getLongWordCount() {
      return longWordCount;
   }

   public double getAverageWordLength() {
      if (words.length == 0) {
         return 0.0;
      }
      return (double) totalLetters / words.length;
   }
}
