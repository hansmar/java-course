import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextReader {

    public String ReadFile() {
        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter path to a text file: ");
                String path = keyboard.nextLine();

                try (Scanner fileScanner = new Scanner(new File(path), "UTF-8")) {
                    StringBuilder content = new StringBuilder();
                    while (fileScanner.hasNextLine()) {
                        content.append(fileScanner.nextLine()).append("\n");
                    }
                    return content.toString();
                }
                catch (FileNotFoundException e) {
                    System.out.println("Could not find file \"" + path + "\". Try again.");
                }
            }
        }
    }
}