import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamExtensions {

    /* Generates a stream of 'random' numbers.
     * a[0] = seed
     * a[n + 1] = mid((a[n])^2)
     */
    public static IntStream pseudoRandomStream(int seed) {
        IntUnaryOperator mid = lastNumber -> lastNumber / 10 % 1000;
        return IntStream.iterate(seed, x -> mid.applyAsInt(x * x));
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        // bad because we have to keep the whole stream
        List<T> array = stream.sorted(order).collect(Collectors.toList());
        if (array.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(array.get(0), array.get(array.size() - 1));
        }
    }

}
