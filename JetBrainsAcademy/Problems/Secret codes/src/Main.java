import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static List<String> extractCodes(List<String> codes) {
        Integer lim = Integer.parseInt("FFFF", 16);
        return codes.stream()
                .map(s -> Integer.parseInt(s.substring(1), 16))
                .dropWhile(Predicate.isEqual(0).negate())
                .skip(1)
                .takeWhile(Predicate.not(Predicate.isEqual(lim)))
                .map(Integer::toHexString)
                .map(String::toUpperCase)
                .map(s -> "#" + s)
                .collect(Collectors.toList());
    }

    /* Please do not modify the code below */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> codes = Arrays.stream(scanner.nextLine()
                .split("\\s+"))
                .collect(Collectors.toList());

        System.out.println(String.join(" ", extractCodes(codes)));
    }
}