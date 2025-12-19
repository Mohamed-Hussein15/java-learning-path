package Model;

public class Account {
    String userName;
    String password;
    double balance;
    String phoneNumber;
    String address;
    float age;

    private boolean isAdmin;
    private boolean isActive = true; // default active

    public Account(){}

    public Account(String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isActive = true;
    }

    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Account(String userName, String password, String phoneNumber, String address, float age) {
        this.userName = userName;
        this.password = password;
        this.balance = 0;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getAge() {
        return age;
    }

    public void setAge(float age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "\n===== Account Information =====\n" +
                "User Name   : " + userName + "\n" +
                "Balance     : " + balance + "\n" +
                "Phone Number: " + phoneNumber + "\n" +
                "Age         : " + age + "\n" +
                "================================\n";
    }

}
