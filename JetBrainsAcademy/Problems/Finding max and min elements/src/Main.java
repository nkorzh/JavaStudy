import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;


class MinMax {
    public static class Statistics<T> implements Consumer<T> {
        private final Comparator<? super T> comparator;
        private T minValue;
        private T maxValue;

        public Statistics(Comparator<? super T> comparator) {
            this.comparator = comparator;
            minValue = maxValue = null;
        }

        @Override
        public void accept(T t) {
            if (minValue == null || maxValue == null) {
                minValue = maxValue = t;
            } else {
                minValue = comparator.compare(minValue, t) > 0 ? t : minValue;
                maxValue = comparator.compare(maxValue, t) < 0 ? t : maxValue;
            }
        }

        public void apply(BiConsumer<? super T, ? super T> minMaxConsumer) {
            minMaxConsumer.accept(minValue, maxValue);
        }
    }

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        Statistics<T> minMaxFinder = new Statistics<>(order);
        stream.forEach(minMaxFinder);
        minMaxFinder.apply(minMaxConsumer);
    }
}