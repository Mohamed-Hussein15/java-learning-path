package Service;

import Model.Account;

public interface AccountService {

    Integer createAccount(Account account);

    Integer transferMoney(Account fromAccount, Account toAccount, double amount);

    boolean getAccountByUsernameAndPassword(Account account);

    boolean getAccountByPhoneNumber(Account account);

    Integer changePassword(Account account, String oldPassword, String newPassword);

    Account findAccountByPhone(String phoneNumber);

    Account getAccountByUsername(Account account);

    Integer deposit(Account account, double amount);

    Integer withdraw(Account account, double amount);
}
