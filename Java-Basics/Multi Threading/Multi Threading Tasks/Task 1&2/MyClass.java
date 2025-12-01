public class MyClass implements Runnable{
    public void run(){
        System.out.println("thread name: " + Thread.currentThread().getName());
        System.out.println("Hello from my class runnable");
    }
}
