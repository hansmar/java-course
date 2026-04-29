public class TextAnalyzer {
   private String text;

   // "has-a" the three level-specific analyzers
   private ParagraphAnalyzer paragraphAnalyzer;
   private SentenceAnalyzer sentenceAnalyzer;
   private WordAnalyzer wordAnalyzer;

   public TextAnalyzer(String text) {
      this.text = text;
   }

   public void analyze() {
      // 1. Paragraph level
      paragraphAnalyzer = new ParagraphAnalyzer(text);
      paragraphAnalyzer.analyze();
      String[] paragraphs = paragraphAnalyzer.getParagraphs();
      int numParagraphs = paragraphAnalyzer.getParagraphCount();

      // 2. Sentence level (feeds on paragraphs)
      sentenceAnalyzer = new SentenceAnalyzer(paragraphs);
      sentenceAnalyzer.analyze();
      String[] sentences = sentenceAnalyzer.getSentences();
      int numSentences = sentenceAnalyzer.getSentenceCount();

      // 3. Word level (feeds on sentences)
      wordAnalyzer = new WordAnalyzer(sentences);
      wordAnalyzer.analyze();
      int numWords = wordAnalyzer.getWordCount();
      int numLongWords = wordAnalyzer.getLongWordCount();
      double averageWordLength = wordAnalyzer.getAverageWordLength();

      // Guard against division by zero on empty input.
      double sentencesPerParagraph = 0.0;
      double wordsPerSentence = 0.0;
      double lixScore = 0.0;

      if (numParagraphs > 0) {
         sentencesPerParagraph = (double) numSentences / numParagraphs;
      }
      if (numSentences > 0) {
         wordsPerSentence = (double) numWords / numSentences;
      }
      if (numWords > 0 && numSentences > 0) {
         // Lix = A/B + 100 * C/A
         // A = number of words, B = number of sentences, C = number of long words
         lixScore = (double) numWords / numSentences
                  + 100.0 * (double) numLongWords / numWords;
      }

      // Print the report
      System.out.println();
      System.out.println("========= Text Analysis Report =========");
      System.out.printf("Number of paragraphs     : %d%n",    numParagraphs);
      System.out.printf("Sentences per paragraph  : %.2f%n",  sentencesPerParagraph);
      System.out.printf("Number of sentences      : %d%n",    numSentences);
      System.out.printf("Words per sentence       : %.2f%n",  wordsPerSentence);
      System.out.printf("Number of words          : %d%n",    numWords);
      System.out.printf("Average word length      : %.2f%n",  averageWordLength);
      System.out.printf("Lix score                : %.2f%n",  lixScore);
      System.out.println("========================================");
   }
}
