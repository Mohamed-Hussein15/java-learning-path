import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<Double> doubles = Arrays.asList(10d, 5d, 3d, 7d, 2d, 10d, 5d, 8d, 9d, 0d, -3d, 4d);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        List<Student> students = Arrays.asList(
                new Student("Ali", "IT", 85),
                new Student("Mona", "CS", 92),
                new Student("Ahmed", "IT", 60),
                new Student("Sara", "CS", 70),
                new Student("Omar", "IS", 45),
                new Student("Laila", "IS", 78)
        );

        List<Employee> employees = Arrays.asList(
                new Employee("Ali", 30, "HR", 5000),
                new Employee("Mona", 25, "IT", 7000),
                new Employee("Ahmed", 30, "HR", 5500),
                new Employee("Sara", 27, "IT", 7200),
                new Employee("Omar", 40, "Finance", 8000),
                new Employee("Laila", 35, "Finance", 8200)
        );

        //Task 4 Q1 Group a list of students by their department.
        Map<String, List<Student>> result1 = students.stream().collect(Collectors.groupingBy(Student::getDepartment));
        System.out.println(result1);

        //Task 4 Q2 Partition a list of numbers into even and odd using partitioningBy.
        Map<Boolean,List<Integer>> result2 = numbers.stream().collect(Collectors.partitioningBy(number -> number % 2 ==0));
        System.out.println("Even numbers: " + result2.get(true) + ", " + "Odd Numbers: " + result2.get(false));

        //Task 4 Q3 Create a comma-separated string from a list of strings.
        String result3 = names.stream().filter(name -> Objects.nonNull(name) && name != "").collect(Collectors.joining(", "));
        System.out.println(result3);

        //Task 4 Q4 Group employees by age and count how many per age.
        Map<Integer, Long> result4= employees.stream().collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));
        System.out.println(result4);

        //Task 4 Q5 Find the average salary per department in a list of employees.
        Map<String, Double> result5 = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(result5);
    }
}