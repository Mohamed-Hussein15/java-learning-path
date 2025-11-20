import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String text = null;


        try {
            System.out.println("Please enter a text or type null for exeption: ");
            text = sc.nextLine();
            if(text.equals("null")) {
                text = null;
            }
            System.out.println("str length = " + text.length());

            System.out.println("Please enter a two integer: ");
            int a = sc.nextInt();
            int b = sc.nextInt();

            int result = a / b;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("can't divide by zero");
        } catch (NullPointerException e) {
            System.out.println("text was null");
        }
    }
}