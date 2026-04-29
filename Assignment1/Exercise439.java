// Encrypt a 4-digit number:
// 1. Add 7 to each digit and taking mod 10
// 2. Swap digits (1↔3, 2↔4)

import java.util.Scanner;

public class Exercise439 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Read input
        System.out.print("Enter a four-digit integer: ");
        int number = input.nextInt();

        // Extract digits
        int d1 = number / 1000;
        int d2 = (number / 100) % 10;
        int d3 = (number / 10) % 10;
        int d4 = number % 10;

        // Print extracted digits
        //System.out.println("Extracted digits: " + d1 + " " + d2 + " " + d3 + " " + d4);

        // Encrypt digits using (digit + 7) % 10
        d1 = (d1 + 7) % 10;
        d2 = (d2 + 7) % 10;
        d3 = (d3 + 7) % 10;
        d4 = (d4 + 7) % 10;

        // Print digits before swap
        //System.out.println("Encrypted digits (before swap): " + d1 + " " + d2 + " " + d3 + " " + d4);

        // Print digits after swap
        //System.out.println("Digits after swap: " + d3 + " " + d4 + " " + d1 + " " + d2);

        // Swap digits: (d1,d2,d3,d4) → (d3,d4,d1,d2) and rebuild encrypted number
        int encrypted = d3 * 1000 + d4 * 100 + d1 * 10 + d2;

        // Output result
        System.out.println("Encrypted integer: " + encrypted);
    }
}