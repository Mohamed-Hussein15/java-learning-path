import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String x = null;
        try {
            x.toUpperCase();
        }catch (NullPointerException e){
            System.out.println("Can't convert null value");
        }

    }
}