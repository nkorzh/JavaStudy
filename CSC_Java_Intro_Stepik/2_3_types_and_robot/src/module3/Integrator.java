package module3;

import java.util.function.DoubleUnaryOperator;

public class Integrator {

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        return f.applyAsDouble(a);
    }
}
