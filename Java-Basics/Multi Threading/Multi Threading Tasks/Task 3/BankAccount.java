public class BankAccount {

    private int balance;

    public synchronized void deposit(int amount){
        balance += amount;
        System.out.println("Deposited: " + amount + " | Current Balance: " + balance);

        notify(); // Notify waiting withdrawers
    }

    public synchronized void withdraw(int amount){
        while (balance < amount) {
            System.out.println("Not enough balance. Waiting...");
            try {
                wait(); // Wait until someone deposits
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount + " | Current Balance: " + balance);

    }
}
