import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        //Task 2 Q1 Count the number of strings longer than 5 characters.
        long result1 = names.stream().filter(name -> Objects.nonNull(name) && name != "" && name.length() > 5).count();
        System.out.println(result1);

        //Task 2 Q2 Find the first element in a stream that matches a given condition.
        Optional<Integer> result2 = numbers.stream().filter(number -> number % 2 ==0).findFirst();
        System.out.println(result2.orElse(-1));

        //Task 2 Q3 Check if any number is divisible by 5 in a list.
        boolean result3 = numbers.stream().anyMatch(number -> number % 5 ==0);
        System.out.println(result3);

        //Task 2 Q4 Collect elements into a Set instead of a List.
        Set<Integer> result4 = numbers.stream().collect(Collectors.toSet());
        System.out.println(result4);

        //Task 2 Q5 Skip the first 3 elements and return the rest.
        List<String> result5 = names.stream().skip(3).collect(Collectors.toList());
        System.out.println(result5);
    }
}