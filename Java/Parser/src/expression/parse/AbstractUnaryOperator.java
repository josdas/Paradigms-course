package expression.parse;

import expression.exception.IllegalOperationException;
import expression.exception.OverflowException;
import expression.exception.ParsingException;
import expression.parse.operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractUnaryOperator<T> implements TripleExpression<T> {
    TripleExpression<T> first;
    protected Operation<T> operation;

    protected abstract T operator(T x) throws OverflowException, IllegalOperationException;

    public AbstractUnaryOperator(TripleExpression<T> t, Operation<T> op) {
        first = t;
        operation = op;
    }

    public T evaluate(T x, T y, T z) throws ParsingException {
        return operator(first.evaluate(x, y, z));
    }
}
