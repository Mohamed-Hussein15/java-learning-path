
public class Main {
    public static void main(String[] args) {
        int x = 5;
        int y = 0;
       try
           {
           try{
               System.out.println(x/y);
           }catch (NullPointerException ex){
               System.out.println("NullPointerException Exception");
           }
           }catch (ArithmeticException ex){
           System.out.println("Arithmetic Exception Division by zero");
       }
    }
}

