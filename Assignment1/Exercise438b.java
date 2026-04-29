import java.util.Scanner;

public class Exercise438b {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter number of terms: ");
        int terms = input.nextInt();

        double e = 1.0;

        for (int i = 1; i <= terms; i++) {

            int factorial = 1;

            // compute i!
            for (int j = 1; j <= i; j++) {
                factorial = factorial * j;
            }

            // add 1 / i! to e
            e = e + 1.0 / factorial;
        }

        System.out.println("Estimated value of e: " + e);
    }
}