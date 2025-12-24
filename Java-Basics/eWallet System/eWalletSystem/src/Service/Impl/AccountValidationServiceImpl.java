package service.impl;

import service.AccountService;
import service.AccountValidationService;

public class AccountValidationServiceImpl implements AccountValidationService {
    private AccountService accountService = new AccountServiceImpl();

    @Override
    public boolean validateUserName(String username) {
        if(username.length() < 3 || !Character.isUpperCase(username.charAt(0))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePassword(String password) {
        if(password.length() < 8){
            return false;
        }
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!()_\\-{}\\[\\]:;\"'<>,.?/]).{8,}$";

        return password.matches(regex);
//        return true;
    }

    @Override
    public boolean validateAge(float age) {
        if (age < 18) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        if (phoneNumber.length() != 13) {
            return false;
        }

        if (!phoneNumber.startsWith("+") || !phoneNumber.substring(1).matches("\\d+")) {
            return false;
        }

        if (!(phoneNumber.startsWith("+2010") ||
                phoneNumber.startsWith("+2011") ||
                phoneNumber.startsWith("+2012") ||
                phoneNumber.startsWith("+2015"))) {
            return false;
        }

        return true;

    }
}
