package expression.parse.operator;

import expression.exception.*;
import expression.parse.AbstractUnaryOperator;
import expression.parse.TripleExpression;
import expression.parse.operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public class Negate<T> extends AbstractUnaryOperator<T> {
    public Negate(TripleExpression<T> t, Operation<T> op) {
        super(t, op);
    }

    protected T operator(T x) throws OverflowException {
        return operation.not(x);
    }
}
