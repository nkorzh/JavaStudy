//import java.util.function.IntUnaryOperator;
import java.util.function.*;

public class Foo {

    Integer bonus = 10;
    IntUnaryOperator bonusAdder = x -> x + bonus;
    public void foo() {
        System.out.println(bonusAdder.applyAsInt(1));
        bonus += 1;
        System.out.println(bonusAdder.applyAsInt(1));
    }

    /** Represents ternary operator
     * @param condition predicate for operator
     * @param ifTrue function if predicate is true
     * @param ifFalse function otherwise
     * @param <T> - function argument
     * @param <U> - return value type
     * @return functional interface - ternary operator
     */
    public static <T, U> Function<T, U> ternaryOperator(
            Predicate<? super T> condition,
            Function<? super T, ? extends U> ifTrue,
            Function<? super T, ? extends U> ifFalse) {
        return t -> condition.test(t) ? ifTrue.apply(t) : ifFalse.apply(t);
    }

}
