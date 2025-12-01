//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        🔹 Basic Thread Creation
//        Create and run a simple thread
//
//        Task: Create a class that extends Thread, override run() method, and print "Hello from thread". Start the thread.
          MyThread thread = new MyThread();
          thread.start();
//        Implement Runnable interface
//
//        Task: Create a class that implements Runnable and prints the current thread name and a message.
          Thread thread2 = new Thread(new MyClass());
          thread2.start();

          //-------------------------------------------------------------------------

//        Thread Sleep and Join
//        Use Thread.sleep()
//
//        Task: Print numbers 1 to 5 with a 1-second delay between each number using sleep().
          for(int i = 1; i <= 5; i++){
              System.out.println(i);
              try {
                  Thread.sleep(1000);
              } catch (InterruptedException e) {
                  System.out.println("Interrupted");
              }
          }

//        Use Thread.join()
//
//        Task: Create two threads. Make the main thread wait for them to finish using join().
           MyThread t1 = new MyThread();
           MyThread t2 = new MyThread();
           t1.start();
           t2.start();
           try {
               t1.join();
               t2.join();
           } catch (InterruptedException e) {
               System.out.println("Interrupted");
           }
           System.out.println("Main thread: Both threads are finished.");
    }
}