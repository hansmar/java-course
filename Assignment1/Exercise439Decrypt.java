// Decrypt a 4-digit number by reversing encryption:
// 1. Undo the digit swap (1↔3, 2↔4)
// 2. Reverse (digit + 7) % 10 using (digit + 3) % 10

import java.util.Scanner;

public class Exercise439Decrypt {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter encrypted four-digit integer: ");
        int number = input.nextInt();

        // Extract digits
        int d1 = number / 1000;
        int d2 = (number / 100) % 10;
        int d3 = (number / 10) % 10;
        int d4 = number % 10;

        // Print extracted digits
        //System.out.println("Extracted digits: " + d1 + " " + d2 + " " + d3 + " " + d4);

        // Undo swap (same swap again)
        int temp1 = d1;
        int temp2 = d2;

        d1 = d3;
        d2 = d4;
        d3 = temp1;
        d4 = temp2;

        // Print digits after reswap
        //System.out.println("Encrypted digits (after reswap): " + d1 + " " + d2 + " " + d3 + " " + d4);

        // Decrypt digits using (digit + 3) % 10
        d1 = (d1 + 3) % 10;
        d2 = (d2 + 3) % 10;
        d3 = (d3 + 3) % 10;
        d4 = (d4 + 3) % 10;

        // Print digits after decryption
        //System.out.println("Encrypted digits (after decryption): " + d1 + " " + d2 + " " + d3 + " " + d4);

        // Rebuild original number
        int original = d1 * 1000 + d2 * 100 + d3 * 10 + d4;

        System.out.println("Decrypted integer: " + original);
    }
}