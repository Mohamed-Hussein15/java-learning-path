public class Withdrawer extends Thread {
    private BankAccount account;

    public Withdrawer(BankAccount account) {
        this.account = account;
    }
    @Override
    public void run() {
        try {
            while (true) {
                account.withdraw(150);
                Thread.sleep(1500);
            }
        }catch (InterruptedException e){
            System.out.println("Withdrawer Interrupted");
        }
    }
}
