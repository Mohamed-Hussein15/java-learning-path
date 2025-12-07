package Service;

import Model.Account;

public interface AccountService {

    Integer createAccount(Account account);

    boolean getAccountByUsernameAndPassword(Account account);

    boolean getAccountByPhoneNumber(Account account);

    Account getAccountByUsername(Account account);

    Integer deposit(Account account, double amount);

    Integer withdraw(Account account, double amount);
}
