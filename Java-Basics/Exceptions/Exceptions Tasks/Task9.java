public class Main {
    public static void main(String[] args) {
        Main object = new Main();
        try{
            object.method2();
        }catch (Exception e){
            System.out.println("Exception caught" + e.getMessage());
        }
    }
    public void method1() throws Exception{
        System.out.println("method1 Exception");
        throw new Exception("throw method1 Exception");
    }
    public void method2() throws Exception{
        System.out.println("method2 Exception");
        method1();
    }
}

