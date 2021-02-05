import java.util.Objects;

class Pair<TypeA, TypeB> {
    private final TypeA first;
    private final TypeB second;

    private Pair(TypeA first, TypeB second) {
        this.first = first;
        this.second = second;
    }

    public TypeA getFirst() {
        return first;
    }

    public TypeB getSecond() {
        return second;
    }

    public static <TypeA, TypeB> Pair<TypeA, TypeB> of(TypeA first, TypeB second) {
        return new Pair<TypeA, TypeB>(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    
}
