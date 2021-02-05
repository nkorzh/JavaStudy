import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    /**
     * Descending words comparator. Words with bigger numbers appear first.
     * Words with equal numbers are sorted lexicographically.
     * @return comparator of Map.Entry<String, Integer>
     */
    private static Comparator<Map.Entry<String, Integer>> getDescendingComparator() {
        return Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                .reversed()
                .thenComparing(Map.Entry::getKey);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in).useDelimiter("[\\p{javaWhitespace}\\p{Punct}]+");

        Map<String, Integer> wordStat = new HashMap<>();
        scanner.forEachRemaining(w -> wordStat.merge(w.toLowerCase(), 1, Integer::sum));

        wordStat.entrySet().stream()
                .sorted(getDescendingComparator())
                .limit(10)
                .forEach(p -> System.out.println(p.getKey()));
    }
}
