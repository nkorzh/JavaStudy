import java.util.Comparator;
import java.util.List;

class Utils {

    public static List<Integer> sortOddEven(List<Integer> numbers) {
        numbers.sort(
                Comparator.comparing(num -> Math.abs((int) num) % 2).reversed()
                        .thenComparing((a, b) -> ((int) a % 2) == 0 ?
                                Integer.compare((int) b, (int) a) :
                                Integer.compare((int) a, (int) b))
        );
        return numbers;
    }
}