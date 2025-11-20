import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int[] num = new int[5];
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the index");
        int index = sc.nextInt();
        try {
            System.out.println(num[index]);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("please enter a number between 0 and 4");
        }
    }
}