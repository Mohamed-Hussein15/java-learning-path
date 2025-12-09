package Service.Impl;

import Model.Account;
import Model.EWalletSystem;
import Service.AccountService;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private static final EWalletSystem eWalletSystem = new EWalletSystem();

//    @Override
//    public boolean createAccount(Account account) {
//
//        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
//        boolean checkPhoneNumber = eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getPhoneNumber().equals(account.getPhoneNumber()));
//        if (optionalAccount.isPresent()) {
//            return false;
//        }
//
//        if (checkPhoneNumber) {
//            return false;
//        }
//
//        eWalletSystem.getAccounts().add(account);
//
//        return true;
//    }

    @Override
    public Integer createAccount(Account account) {

        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        boolean checkPhoneNumber = eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getPhoneNumber().equals(account.getPhoneNumber()));
        if (optionalAccount.isPresent()) {
            return 1;
        }

        if (checkPhoneNumber) {
            return 2;
        }

        eWalletSystem.getAccounts().add(account);

        return 3;
    }

    @Override
    public Integer transferMoney(Account fromAccount, Account toAccount, double amount) {
        boolean fromExists = getAccountByPhoneNumber(fromAccount);
        boolean toExists = getAccountByPhoneNumber(toAccount);

        if (!fromExists) return 1;   // from account does not exist
        if (!toExists) return 2;     // to account does not exist
        if (fromAccount.getPhoneNumber().equals(toAccount.getPhoneNumber()))
            return 3;  // same account
        if (amount <= 0)
            return 4;                     // invalid amount
        if (amount < 100)
            return 5;                    // below minimum amount
        if (fromAccount.getBalance() < amount)
            return 6; // insufficient balance

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        return 7;
    }

    @Override
    public boolean getAccountByUsernameAndPassword(Account account) {
        return eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getUserName().equals(account.getUserName()) &&
                acc.getPassword().equals(account.getPassword()));
    }

    @Override
    public boolean getAccountByPhoneNumber(Account account) {
        return eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getPhoneNumber().equals(account.getPhoneNumber()));
    }

    @Override
    public Integer changePassword(Account account, String oldPassword, String newPassword) {
//        Account matchingAccount = findAccountByPassword(account.getPassword());
        if  (!account.getPassword().equals(oldPassword)) {
            return 1;
        }
        if (oldPassword.equals(newPassword)) {
            return 2;  // New password same as old
        }
        account.setPassword(newPassword);
            return 3;
    }


    public Account findAccountByPassword(String password) {
        Optional<Account> optionalAccount = findOptionalAccountByPassword(password);
        if (optionalAccount.isEmpty()) {
            return null;
        }
        return optionalAccount.get();

    }

    private Optional<Account> findOptionalAccountByPassword(String password) {
        return eWalletSystem.getAccounts().stream().filter(acc -> acc.getPassword().equals(password)).findFirst();
    }

    @Override
    public Account findAccountByPhone(String phoneNumber) {
        return eWalletSystem.getAccounts().stream().filter(acc -> acc.getPhoneNumber().equals(phoneNumber)).findFirst().orElse(null);
    }

    @Override
    public Account getAccountByUsername(Account account) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        if (optionalAccount.isEmpty()) {
            return null;
        }
        return optionalAccount.get();
    }

    @Override
    public Integer deposit(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        if (optionalAccount.isEmpty()) {
            return 1;
        }

        if (amount < 100) {
            return 2;
        }

        Account accountToDeposit = optionalAccount.get();
        accountToDeposit.setBalance(accountToDeposit.getBalance() + amount);

        return 3;
    }

    @Override
    public Integer withdraw(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);

        if (optionalAccount.isEmpty()) {
            return 1;
        }
        if (amount < 100) {
            return 2;
        }

        Account accountWithdraw = optionalAccount.get();
        if (accountWithdraw.getBalance() < amount) {
            return 3;
        }
        accountWithdraw.setBalance(accountWithdraw.getBalance() - amount);
        return 4;
    }

    private Optional<Account> getOptionalAccountByUsername(Account account) {

        return eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName())).findFirst();
    }
}
