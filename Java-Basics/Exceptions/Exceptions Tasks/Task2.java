import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter a number: ");
        String number = input.nextLine();
        try {
            int num = Integer.parseInt(number);
            System.out.println(num);
        }catch(NumberFormatException e) {
            System.out.println("Error: Please enter a number");
        }
    }
}