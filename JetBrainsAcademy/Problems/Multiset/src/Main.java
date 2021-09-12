import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

interface Multiset<E> {

    /**
     * Add an element to the multiset.
     * It increases the multiplicity of the element by 1.
     */
    void add(E elem);

    /**
     * Remove an element from the multiset.
     * It decreases the multiplicity of the element by 1.
     */
    void remove(E elem);

    /**
     * Unite this multiset with another one. The result is the modified multiset (this).
     * It will contain all elements that are present in at least one of the initial multisets.
     * The multiplicity of each element is equal to the maximum multiplicity of
     * the corresponding elements in both multisets.
     */
    void union(Multiset<E> other);

    /**
     * Intersect this multiset with another one. The result is the modified multiset (this).
     * It will contain all elements that are present in the both multisets.
     * The multiplicity of each element is equal to the minimum multiplicity of
     * the corresponding elements in the intersecting multisets.
     */
    void intersect(Multiset<E> other);

    /**
     * Returns multiplicity of the given element.
     * If the set doesn't contain it, the multiplicity is 0
     */
    int getMultiplicity(E elem);

    /**
     * Check if the multiset contains an element,
     * i.e. the multiplicity > 0
     */
    boolean contains(E elem);

    /**
     * The number of unique elements,
     * that is how many different elements there are in a multiset.
     */
    int numberOfUniqueElements();

    /**
     * The size of the multiset, including repeated elements
     */
    int size();

    /**
     * The set of unique elements (without repeating)
     */
    Set<E> toSet();
}

class HashMultiset<E> implements Multiset<E> {

    private Map<E, Integer> map = new HashMap<>();

    @Override
    public void add(E elem) {
        map.merge(elem, 1, Integer::sum);
    }

    @Override
    public void remove(E elem) {
        map.merge(elem, -1, Integer::sum);
        if (map.getOrDefault(elem, 0) <= 0) {
            map.remove(elem);
        }
    }

    private void leaveIf(final Predicate<Map.Entry<E, Integer>> filterPredicate,
                         final Function<Map.Entry<E, Integer>, Integer> getNewValue) {
        map = map.entrySet()
                .stream()
                .filter(filterPredicate)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        getNewValue));
    }

    @Override
    public void union(final Multiset<E> other) {
        leaveIf(
                e -> true,
                e -> Math.max(e.getValue(), other.getMultiplicity(e.getKey()))
        );
    }

    @Override
    public void intersect(final Multiset<E> other) {
        leaveIf(
                e -> other.getMultiplicity(e.getKey()) > 0,
                e -> Math.min(e.getValue(), other.getMultiplicity(e.getKey()))
        );
    }

    @Override
    public int getMultiplicity(E elem) {
        return map.getOrDefault(elem, 0);
    }

    @Override
    public boolean contains(E elem) {
        return map.containsKey(elem);
    }

    @Override
    public int numberOfUniqueElements() {
        return map.keySet().size();
    }

    @Override
    public int size() {
        return map.values().stream().reduce(0, Integer::sum);
    }

    @Override
    public Set<E> toSet() {
        // Creating a new HashSet<> object helps us avoid ConcurrentModificationException.
        // It is thrown when we try to iterate over elements of Map and modify them at the same time
        return new HashSet<>(map.keySet());
    }
}