import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        //Task 1 Q1 Filter even numbers from a list of integers.
        List <Integer> result = numbers.stream().filter(number -> number % 2 == 0).collect(Collectors.toList());
        System.out.println(result);

        //Task 1 Q2 Find names starting with a specific letter from a list of strings.
        List <String> result2 = names.stream().filter(name -> Objects.nonNull(name) && name != "" && name.startsWith("A")).collect(Collectors.toList());
        System.out.println(result2);

        //Task 1 Q3 Convert all strings to uppercase using stream.
        List <String> result3 = names.stream().filter(name -> Objects.nonNull(name) && name != "").map(name -> name.toUpperCase()).collect(Collectors.toList());
        System.out.println(result3);

        //Task 1 Q4 Sort a list of integers in descending order using streams.
        List <Integer> result4 = numbers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(result4);

        //Task 1 Q5 Remove duplicate elements from a list using distinct().
        List <Integer> result5 = numbers.stream().distinct().collect(Collectors.toList());
        System.out.println(result5);

    }
}