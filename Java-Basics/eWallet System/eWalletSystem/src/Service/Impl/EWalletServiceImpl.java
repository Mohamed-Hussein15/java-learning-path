package Service.Impl;

import Model.Account;
import Service.AccountService;
import Service.AccountValidationService;
import Service.ApplicationService;
import java.util.Objects;
import java.util.Scanner;

public class EWalletServiceImpl implements ApplicationService {
    private AccountService accountService = new AccountServiceImpl();

    private AccountValidationService accountValidationService = new AccountValidationServiceImpl();

    @Override
    public void startApp() {
        System.out.println("Welcome");
        boolean isExit = false;
        int counter = 0;
        while(true){
            System.out.println("Pls enter your service");
            System.out.println("1.login           2.signup            3.Exit");

            Scanner sc = new Scanner(System.in);
            int service = sc.nextInt();

            switch (service){
                case 1:
                    login();
                    break;
                case 2:
                    signup();
                    break;
                case 3:
                    System.out.println("have a nice day");
                    isExit = true;
                    break;
                default:
                    System.out.println("invalid choice");
                    counter++;
            }
            if(isExit){
                break;
            }

            if(counter == 3){
                System.out.println("please contact the administrator");
                break;
            }

        }
    }

    private void login(){

        Account account = getAccount(true);

        if(Objects.isNull(account)){
            return;
        }
        boolean loginSuccess = accountService.getAccountByUsernameAndPassword(account);
        if(loginSuccess){
            System.out.println("login successful");
            profile(account);
        } else {
            System.out.println("invalid username or password");
        }

    }

//    private void signup(){
//
//        Account account = getAccount(false);
//        if(Objects.isNull(account)){
//            return;
//        }
//        boolean isAccountCreated = accountService.createAccount(account);
//        if(isAccountCreated){
//            System.out.println("Account created successfully");
//            profile(account);
//        }
//        else{
//            System.out.println("Account already exist with same username " + account.getUserName());
//        }
//    }

    private void signup(){

        Account account = getAccount(false);
        if(Objects.isNull(account)){
            return;
        }
        Integer isAccountCreated = accountService.createAccount(account);
        if(isAccountCreated == 3){
            System.out.println("Account created successfully");
            profile(account);
        }
        else if (isAccountCreated == 2){
            System.out.println("Account already exist with same phone number " + account.getPhoneNumber());
        }
        else if (isAccountCreated == 1){
            System.out.println("Account already exist with same username " + account.getUserName());
        }
    }

    public Account getAccount(boolean login){
        Scanner sc = new Scanner(System.in);

        System.out.println("please enter your username");
        String username = sc.nextLine();

        if(!accountValidationService.validateUserName(username)){
            System.out.println("invalid username, username must contain at least three characters and starts with uppercase letter");
            return null;
        }

        System.out.println("please enter your password");
        String password = sc.nextLine();

        if(!accountValidationService.validatePassword(password)){
            System.out.println("invalid password, password must contain at least 8 characters, contain 1 Uppercase, 1 Lowercase, 1 Digit, 1 Special Character");
            return null;
        }

        if(login){
            return new Account(username, password);
        }
        System.out.println("please enter your phone number");
        String phoneNumber = sc.nextLine();

        if(!accountValidationService.validatePhoneNumber(phoneNumber)){
            System.out.println("invalid phone number, phone number must be 13 characters, start with +, and be a valid Egyptian number (+2010, +2011, +2012, or +2015)");
            return null;
        }

        System.out.println("please enter your address");
        String address = sc.nextLine();

        System.out.println("please enter your age");
        float age = sc.nextFloat();
        if (!accountValidationService.validateAge(age)){
            System.out.println("invalid age, age must be greater than or equal to 18");
            return null;
        }

        return new Account(username, password, phoneNumber, address, age);
    }
    private void profile(Account account){
        boolean logout = false;
        int counter = 0;
        while (true){
            System.out.println("-----------> Services <-----------");
            System.out.println("1.deposit        2.withdraw        3.show account details        4.logout");
            Scanner sc = new Scanner(System.in);
            System.out.println("please choose the service needed");
            int choice = sc.nextInt();

            switch (choice){
                case 1:
                    deposit(account);
                    break;
                case 2:
                    withdraw(account);
                    break;
                case 3:
                    showAccountDetails(account);
                    break;
                case 4:
                    System.out.println("have a nice day");
                    logout = true;
                    break;
                default:
                    System.out.println("invalid choice");
                    counter++;
            }

            if(logout){
                break;
            }

            if(counter == 3){
                System.out.println("please contact the administrator");
                break;
            }
        }
    }

    private void showAccountDetails(Account account) {
        Account accountExist = accountService.getAccountByUsername(account);
        if(Objects.isNull(accountExist)){
            System.out.println("Account does not exist");
            return;
        }

        System.out.println(account);

    }

    private void withdraw(Account account) {
        System.out.println("please enter the amount you want to withdraw");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        Integer withdrawSuccess = accountService.withdraw(account, amount);

        if(withdrawSuccess == 4){
            System.out.println("withdraw success, your balance: " + account.getBalance());
        }

        else if(withdrawSuccess == 3){
            System.out.println("no enough balance");
        }

        else if(withdrawSuccess == 2){
            System.out.println("amount must be greater than or equal to 100");
        }

        else if (withdrawSuccess == 1) {
            System.out.println("account not found");
        }


    }

    private void deposit(Account account) {
        System.out.println("please enter the amount you want to deposit");
        Scanner sc = new Scanner(System.in);
        double amount = sc.nextDouble();
        Integer depositSuccess = accountService.deposit(account, amount);

        if(depositSuccess == 3){
            System.out.println("deposit success, your balance: " + account.getBalance());
        }

        else if(depositSuccess == 2){
            System.out.println("amount must be greater than or equal to 100");
        }

        else if (depositSuccess == 1) {
            System.out.println("account not found");
        }
    }
}
