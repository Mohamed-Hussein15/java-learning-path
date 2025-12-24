package service;

public interface AccountValidationService {

    boolean validateUserName(String username);
    boolean validatePassword(String password);
    boolean validateAge(float age);
    boolean validatePhoneNumber(String phoneNumber);

}
