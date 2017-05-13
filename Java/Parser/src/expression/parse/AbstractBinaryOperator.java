package expression.parse;


import expression.exception.IllegalOperationException;
import expression.exception.OverflowException;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    TripleExpression<T> first, second;
    protected Operation<T> operation;

    public AbstractBinaryOperator(TripleExpression<T> a, TripleExpression<T> b, Operation<T> op) {
        first = a;
        second = b;
        operation = op;
    }

    protected abstract T operator(T a, T b) throws OverflowException, IllegalOperationException;

    public T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException {
        return operator(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
