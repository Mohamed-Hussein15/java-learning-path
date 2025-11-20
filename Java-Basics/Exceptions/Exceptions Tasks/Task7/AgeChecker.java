public class AgeChecker {
    private int age;

    public void ageCheck(int age) throws InvalidAgeException{
        if (age < 18) {
            throw new InvalidAgeException("Age is less than 18", "CODE_1122");
        }
        else
            {
                System.out.println("Access granted");;
            }
    }
}
