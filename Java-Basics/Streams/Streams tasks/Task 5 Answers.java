import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 5, 3, 7, 2, 10, 5, 8, 9, 0, -3, 4);
        List<Double> doubles = Arrays.asList(10d, 5d, 3d, 7d, 2d, 10d, 5d, 8d, 9d, 0d, -3d, 4d);
        List<String> names = Arrays.asList("Ali", "Mona", "Ahmed", "Sara", "Amr", "Laila", "Kareem", "Nada", "Nour", "Samy", "", null);

        List<Optional<String>> optionalList = Arrays.asList(
                Optional.of("Java"),
                Optional.empty(),
                Optional.of("Stream"),
                Optional.ofNullable(null),
                Optional.of("Lambda")
        );

        List<List<String>> nestedWords = Arrays.asList(
                Arrays.asList("Java", "Stream"),
                Arrays.asList("API", "Lambda"),
                Arrays.asList("FlatMap", "Map")
        );

        //Task 5 Q1 Flatten a list of lists into a single list.
        List<String> result1 = nestedWords.stream().flatMap(l -> l.stream()).collect(Collectors.toList());
        System.out.println(result1);

        //Task 5 Q2 Extract all unique characters from a list of words.
        List<Character> result2 = names.stream().filter(name -> Objects.nonNull(name) && !name.equals("")).map(name -> name.split("")).flatMap(Arrays::stream).map(c -> c.charAt(0)).distinct().collect(Collectors.toList());
        System.out.println(result2);

        //Task 5 Q3 Filter a list of Optionals and collect non-empty values.
        List<String> result3 = optionalList.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        System.out.println(result3);

        //Task 5 Q4 Map a list of strings to their lengths.
        List<Integer> result4 = names.stream().filter(name -> name != null &&!name.isEmpty()  ).map(name -> name.length()).collect(Collectors.toList());
        System.out.println(result4);

        //Task 5 Q5 Return a list of uppercased words that start with “A”.
        List<String> result5 = names.stream().filter(name -> name != null && !name.isEmpty() && name.startsWith("A")).map(name -> name.toUpperCase()).collect(Collectors.toList());
        System.out.println(result5);
    }
}