import java.util.Scanner;

public class Exercise438c {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Read input
        System.out.print("Enter x: ");
        int x = input.nextInt();

        System.out.print("Enter number of terms: ");
        int terms = input.nextInt();

        double result = 1.0;

        // Loop through terms
        for (int i = 1; i <= terms; i++) {

            int factorial = 1;
            int power = 1;

            // Compute i!
            for (int j = 1; j <= i; j++) {
                factorial = factorial * j;
            }

            // Compute x^i
            for (int j = 1; j <= i; j++) {
                power = power * x;
            }

            // Add term to result
            result = result + (double) power / factorial;
        }

        // Output result
        System.out.println("Estimated value of e^x: " + result);
    }
}