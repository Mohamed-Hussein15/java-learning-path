//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        this task contain wait and notify pls search about this and apply
//
//📄 Task Description:
//        Create a BankAccount class shared between two types of threads:
//
//        Depositor Thread: adds money to the account.
//
//        Withdrawer Thread: takes money from the account.
//
//        You will:
//
//        Start one Depositor thread and one Withdrawer thread.
//
//        The Depositor will deposit money every few seconds.
//
//        The Withdrawer will try to withdraw money every few seconds.
//
//        Use synchronized and wait/notify to make it thread-safe.
        BankAccount account = new BankAccount();
        Depositor depositor = new Depositor(account);
        Withdrawer withdrawer = new Withdrawer(account);

        depositor.start();
        withdrawer.start();
    }
}