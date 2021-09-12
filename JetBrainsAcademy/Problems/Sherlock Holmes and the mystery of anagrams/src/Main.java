import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Function<String, Map<Integer, Integer>> buildStat = s ->
                s.toLowerCase()
                .chars()
                .boxed()
                .collect(Collectors.toMap(
                        UnaryOperator.identity(),
                        u -> 1,
                        Integer::sum,
                        HashMap::new));

        Map<Integer, Integer> stat1 = buildStat.apply(scanner.next());
        Map<Integer, Integer> stat2 = buildStat.apply(scanner.next());
        System.out.println(stat1.equals(stat2) ? "yes" : "no");
    }
}