package Service;

import Model.Account;

public interface AccountService {

    void createAccount(Account account);

    void transferMoney(Account fromAccount, Account toAccount, double amount);

    boolean getAccountByUsernameAndPassword(Account account);

    boolean getAccountByPhoneNumber(Account account);

    void changePassword(Account account, String oldPassword, String newPassword);

    Account findAccountByPhone(String phoneNumber);

    Account getAccountByUsername(Account account);

    void deposit(Account account, double amount);

    void withdraw(Account account, double amount);
}
