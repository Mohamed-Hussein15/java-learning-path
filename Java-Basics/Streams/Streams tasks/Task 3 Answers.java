import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<Double> doubles = Arrays.asList(10d, 5d, 3d, 7d, 2d, 10d, 5d, 8d, 9d, 0d, -3d, 4d);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        //Task 3 Q1 Calculate the sum of a list of integers using reduce.
        Integer result1 = numbers.stream().reduce(0, Integer::sum);
        System.out.println(result1);

        //Task 3 Q2 Find the maximum and minimum value in a list.
        Optional<Integer> result2Max = numbers.stream().max(Integer::compareTo);
        Optional<Integer> result2Min = numbers.stream().min(Integer::compareTo);
        result2Max.ifPresent(System.out::println);
        result2Min.ifPresent(System.out::println);

        //Task 3 Q3 Calculate the average of a list of doubles.
        OptionalDouble result3 = doubles.stream().mapToDouble(Double::doubleValue).average();
        result3.ifPresent(System.out::println);

        //Task 3 Q4 Multiply all integers in a list together using reduce.
        Optional<Integer> result4 = numbers.stream().reduce((a, b) -> a * b);
        result4.ifPresent(System.out::println);

        //Task 3 Q5 Count how many numbers are positive in a list.
        long result5 = numbers.stream().filter(number -> number > 0).count();
        System.out.println(result5);
    }
}