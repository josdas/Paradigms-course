package expression.parse;

import expression.exception.IllegalOperationException;
import expression.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException;
}
