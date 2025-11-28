import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 5, 8, 9, 0, -3, 4);
        List<Double> doubles = Arrays.asList(10d, 5d, 3d, 7d, 2d, 10d, 5d, 8d, 9d, 0d, -3d, 4d);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        List<Optional<String>> optionalList = Arrays.asList(
                Optional.of("Java"),
                Optional.empty(),
                Optional.of("Stream"),
                Optional.ofNullable(null),
                Optional.of("Lambda")
        );

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

        List<List<String>> nestedWords = Arrays.asList(
                Arrays.asList("Java", "Stream"),
                Arrays.asList("API", "Lambda"),
                Arrays.asList("FlatMap", "Map")
        );

        //Task 6 Q1 Sort a list of employees by salary then by name.
        List<Employee> result1 = employees.stream().sorted(Comparator.comparing(Employee::getSalary).thenComparing(Comparator.comparing(Employee::getName))).collect(Collectors.toList());
        System.out.println(result1);

        //Task 6 Q2 Find the second-highest number in a list.
        Integer result2 = numbers.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
        System.out.println(result2);

        //Task 6 Q3 Find duplicate elements in a list of integers.
        Set<Integer> exist = new HashSet<>();
        Set<Integer> result3 = numbers.stream().filter(number -> !exist.add(number)).collect(Collectors.toSet());
        System.out.println(result3);

        //Task 6 Q4 Remove null or empty strings from a list using stream.
        List<String> result4 = names.stream().filter(name -> Objects.nonNull(name) && !name.isEmpty()).collect(Collectors.toList());
        System.out.println(result4);

        //Task 6 Q5 Partition students into pass/fail groups based on grade.
        Map<String,List<Student>> result5 = students.stream().collect(Collectors.groupingBy(student -> student.getGrade() >= 50 ? "Pass" : "Fail"));
        System.out.println(result5);
    }
}