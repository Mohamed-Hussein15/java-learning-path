public class Depositor extends Thread {
    private BankAccount account;

    public Depositor(BankAccount account) {
        this.account = account;
    }
    @Override
    public void run() {
        try{
            while (true) {
                account.deposit(100);
                Thread.sleep(2000);
            }
        }catch(InterruptedException e){
            System.out.println("Depositor interrupted");
        }

    }
}
