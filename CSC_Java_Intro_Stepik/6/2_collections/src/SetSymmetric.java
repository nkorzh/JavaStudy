import java.util.HashSet;
import java.util.Set;

/** Find symmetric diffrerence for two sets
 */
public class SetSymmetric {

    public static <T> Set<T> symmetricDifference(Set<? extends T> set1, Set<? extends T> set2) {
        Set<T> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<T> result = new HashSet<>(set1);
        result.addAll(set2);
        result.removeIf(intersection::contains);
        return result;
    }
}
