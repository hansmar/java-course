import java.util.Scanner;

public class Exercise437 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Read the first two input points from the user
        System.out.print("Enter x1: ");
        int x1 = input.nextInt();

        System.out.print("Enter y1: ");
        int y1 = input.nextInt();

        // Read the second two input points from the user
        System.out.print("Enter x2: ");
        int x2 = input.nextInt();

        System.out.print("Enter y2: ");
        int y2 = input.nextInt();

        // Check if a line is perpendicular to an axis
        if ((x1 == x2) && (y1 == y2)) {
            System.out.println("The points are the same (no line, just a dot)");
        } else if (x1 == x2) {
            System.out.println("Vertical line (perpendicular to x-axis)");
        } else if (y1 == y2) {
            System.out.println("Horizontal line (perpendicular to y-axis)");
        } else {
            System.out.println("Not perpendicular to an axis");
        }
    }
}