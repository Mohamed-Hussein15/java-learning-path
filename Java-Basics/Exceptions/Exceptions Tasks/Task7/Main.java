import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
      AgeChecker ageChecker = new AgeChecker();
      int age;
      Scanner input = new Scanner(System.in);
      System.out.println("Please enter your age");
      age = input.nextInt();
      try {
          ageChecker.ageCheck(age);
      } catch (InvalidAgeException e) {
          System.out.println(e.getFailMessage());
      }
    }
}