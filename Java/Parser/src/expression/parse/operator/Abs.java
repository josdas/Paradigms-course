package expression.parse.operator;

import expression.exception.OverflowException;
import expression.parse.AbstractUnaryOperator;
import expression.parse.TripleExpression;
import expression.parse.operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public class Abs<T> extends AbstractUnaryOperator<T> {
    public Abs(TripleExpression<T> t, Operation<T> op) {
        super(t, op);
    }

    protected T operator(T x) throws OverflowException {
        return operation.abs(x);
    }
}
