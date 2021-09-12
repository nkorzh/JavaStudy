import java.util.function.Function;
import java.util.function.Predicate;

class Counter {

    public static boolean checkTheSameNumberOfTimes(int elem, List<Integer> list1, List<Integer> list2) {
        Function<List<Integer>, Long> countElement = list ->
                list.stream()
                        .filter(Predicate.isEqual(elem))
                        .count();
        return countElement.apply(list1).equals(countElement.apply(list2));
    }
}