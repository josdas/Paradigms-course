package expression.parse.operator;

import expression.exception.IllegalOperationException;
import expression.exception.OverflowException;
import expression.parse.AbstractUnaryOperator;
import expression.parse.TripleExpression;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public class Sqr<T> extends AbstractUnaryOperator<T> {
    public Sqr(TripleExpression<T> t, Operation<T> op) {
        super(t, op);
    }

    protected T operator(T x) throws OverflowException, IllegalOperationException {
        return operation.sqr(x);
    }
}
