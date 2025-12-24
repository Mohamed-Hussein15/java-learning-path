package util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    public static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a number:");
            }
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a valid amount:");
            }
        }
    }

    public static float readFloat() {
        while (true) {
            try {
                return Float.parseFloat(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a valid number:");
            }
        }
    }

    public static String readString() {
        return sc.nextLine();
    }
}
