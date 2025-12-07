package Service.Impl;

import Model.Account;
import Model.EWalletSystem;
import Service.AccountService;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    EWalletSystem eWalletSystem = new EWalletSystem();

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
    public boolean getAccountByUsernameAndPassword(Account account) {
        return eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getUserName().equals(account.getUserName()) &&
                acc.getPassword().equals(account.getPassword()));
    }

    @Override
    public boolean getAccountByPhoneNumber(Account account) {
        return eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getPhoneNumber().equals(account.getPhoneNumber()));
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
