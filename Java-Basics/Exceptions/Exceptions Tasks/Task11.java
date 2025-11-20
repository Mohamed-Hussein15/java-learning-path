import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x;
        int y;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter two numbers");
        x = sc.nextInt();
        y = sc.nextInt();
        try {
            int result = x/y;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Division by zero");
        } finally {
            System.out.println("Finally block");
        }
    }
}

