package Service.Impl;

import Exceptions.*;
import Model.Account;
import Model.EWalletSystem;
import Service.AccountService;
import Service.HistoryService;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private static final EWalletSystem eWalletSystem = new EWalletSystem();
    private HistoryService historyService = new HistoryServiceImpl();

    static {
        Account admin = new Account("IAM", "Iam@123123", true);
        eWalletSystem.getAccounts().add(admin);
    }

    public static EWalletSystem getSystem() {
        return eWalletSystem;
    }

    @Override
    public void createAccount(Account account) {

        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        boolean checkPhoneNumber = eWalletSystem.getAccounts().stream().anyMatch(acc -> acc.getPhoneNumber() != null && acc.getPhoneNumber().equals(account.getPhoneNumber()));

        if (optionalAccount.isPresent()) {
            throw new AccountAlreadyExistException("Account already exist with same username" + account.getUserName());
        }

        if (checkPhoneNumber) {
            throw new AccountAlreadyExistException("Account already exist with same phone number " + account.getPhoneNumber());
        }

        eWalletSystem.getAccounts().add(account);
        historyService.log(account.getUserName(), "SIGNUP", true);
    }

    @Override
    public void deleteAccount(String username) {

        Optional<Account> accountOpt = eWalletSystem.getAccounts()
                .stream()
                .filter(acc -> acc.getUserName().equals(username))
                .findFirst();

        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException(
                    "Account with username '" + username + "' does not exist"
            );
        }

        Account account = accountOpt.get();

        if (account.isAdmin()) {
            throw new IllegalOperationException("Admin account cannot be deleted");
        }

        eWalletSystem.getAccounts().remove(account);
    }



    @Override
    public void deactivateAccount(String username) {

        Optional<Account> accountOpt = eWalletSystem.getAccounts()
                .stream()
                .filter(acc -> acc.getUserName().equals(username))
                .findFirst();

        if (accountOpt.isEmpty()) {
            throw new AccountNotFoundException(
                    "Account with username '" + username + "' does not exist"
            );
        }

        Account account = accountOpt.get();

        if (account.isAdmin()) {
            throw new IllegalOperationException("Admin account cannot be inactivated");
        }

        if (!account.isActive()) {
            throw new IllegalOperationException("Account is already inactive");
        }

        account.setActive(false);
    }



    @Override
    public void transferMoney(Account fromAccount, Account toAccount, double amount) {
        boolean fromExists = getAccountByPhoneNumber(fromAccount);
        boolean toExists = getAccountByPhoneNumber(toAccount);

        if (!fromExists) {
            throw new AccountNotFoundException("Sender account does not exist!");
        }
        if (!toExists) {
            throw new AccountNotFoundException("Receiver account does not exist!");
        }
        if (fromAccount.getPhoneNumber().equals(toAccount.getPhoneNumber()))
            throw new SameAccountException("Cannot transfer to the same account!");
        if (amount <= 0)
            throw new InvalidAmountException("Invalid amount! Amount must be > 0.");
        if (amount < 100)
            throw new InvalidAmountException("Minimum allowed transfer is 100 EGP.");
        if (fromAccount.getBalance() < amount)
            throw new InsufficientBalanceException("Insufficient balance!");

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
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
    public void changePassword(Account account, String oldPassword, String newPassword) {
        if  (!account.getPassword().equals(oldPassword)) {
//            return 1;
            throw new InvalidCredentialsException("Incorrect old password.");
        }
        if (oldPassword.equals(newPassword)) {
//            return 2;  // New password same as old
            throw new InvalidCredentialsException("New password cannot be same as old password.");
        }
        account.setPassword(newPassword);
    }


    public Account findAccountByPassword(String password) {
        Optional<Account> optionalAccount = findOptionalAccountByPassword(password);
        if (optionalAccount.isEmpty()) {
//            return null;
            throw new AccountNotFoundException("Account not found");
        }
        return optionalAccount.get();

    }

    private Optional<Account> findOptionalAccountByPassword(String password) {
        return eWalletSystem.getAccounts().stream().filter(acc -> acc.getPassword().equals(password)).findFirst();
    }

    @Override
    public Account findAccountByPhone(String phoneNumber) {
        return eWalletSystem.getAccounts()
                .stream()
                .filter(acc -> acc.getPhoneNumber().equals(phoneNumber))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account with phone " + phoneNumber + " not found"));
    }

    @Override
    public Account getAccountByUsername(Account account) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        if (optionalAccount.isEmpty()) {
//            return null;
            throw new AccountNotFoundException("Account not found");
        }
        return optionalAccount.get();
    }

    @Override
    public void deposit(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);
        if (optionalAccount.isEmpty()) {
//            return 1;
            throw new AccountNotFoundException("Account not found");
        }

        if (amount < 100) {
//            return 2;
            throw new InvalidAmountException("Amount must be greater than or equal to 100");
        }

        Account accountToDeposit = optionalAccount.get();
        accountToDeposit.setBalance(accountToDeposit.getBalance() + amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        Optional<Account> optionalAccount = getOptionalAccountByUsername(account);

        if (optionalAccount.isEmpty()) {
//            return 1;
            throw new AccountNotFoundException("Account not found");
        }
        if (amount < 100) {
//            return 2;
            throw new InvalidAmountException("Amount must be greater than or equal to 100");
        }

        Account accountWithdraw = optionalAccount.get();
        if (accountWithdraw.getBalance() < amount) {
//            return 3;
            throw new InsufficientBalanceException("Insufficient balance");
        }
        accountWithdraw.setBalance(accountWithdraw.getBalance() - amount);
    }

    private Optional<Account> getOptionalAccountByUsername(Account account) {

        return eWalletSystem.getAccounts().stream()
                .filter(acc -> acc.getUserName().equals(account.getUserName())).findFirst();
    }
}
