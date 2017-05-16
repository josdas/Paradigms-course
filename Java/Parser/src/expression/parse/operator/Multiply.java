package expression.parse.operator;

import expression.exception.OverflowException;
import expression.parse.AbstractBinaryOperator;
import expression.parse.TripleExpression;
import expression.parse.operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public class Multiply<T> extends AbstractBinaryOperator<T> {
    public Multiply(TripleExpression<T> a, TripleExpression<T> b, Operation<T> op) {
        super(a, b, op);
    }

    protected T operator(T a, T b) throws OverflowException {
        return operation.mul(a, b);
    }
}
