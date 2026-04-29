import java.util.Scanner;

public class Exercise436 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Read two input numbers
        System.out.print("Enter first number: ");
        int num1 = input.nextInt();

        System.out.print("Enter second number: ");
        int num2 = input.nextInt();

        // Compare the input numbers
        if (num1 == num2) {
            System.out.println(0);
        } else if (num1 > num2) {
            System.out.println(1);
        } else {
            System.out.println(-1);
        }
    }
}