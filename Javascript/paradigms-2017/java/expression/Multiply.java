package expression;

/**
 * Created by Stas on 28.03.2017.
 */
public class Multiply extends AbstractBinaryOperator{

    public Multiply(ExpressionAll a, ExpressionAll b) {
        super(a, b);
    }

    protected int operator(int a, int b) {
        return a * b;
    }

    protected double operator(double a, double b) {
        return a * b;
    }
}
