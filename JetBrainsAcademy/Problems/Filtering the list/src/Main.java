import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] tokens = scanner.nextLine().split("\\s+");
        List<Integer> list = Stream.iterate(1, integer -> integer + 2)
                .limit(tokens.length / 2)
                .map(i -> Integer.parseInt(tokens[i]))
                .collect(Collectors.toList());
        Collections.reverse(list);
        list.forEach(i -> System.out.print(i + " "));
    }
}