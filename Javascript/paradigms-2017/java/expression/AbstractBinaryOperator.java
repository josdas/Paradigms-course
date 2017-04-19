package expression;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractBinaryOperator implements ExpressionAll{
    ExpressionAll first, second;

    public AbstractBinaryOperator(ExpressionAll a, ExpressionAll b) {
        first = a;
        second = b;
    }

    protected abstract int operator(int a, int b);

    protected abstract double operator(double a, double b);

    public double evaluate(double x) {
        return operator(first.evaluate(x), second.evaluate(x));
    }

    public int evaluate(int x) {
        return operator(first.evaluate(x), second.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return operator(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
