package expression.parse.Operators;

import expression.exception.OverflowException;
import expression.exception.ParsingException;

/**
 * Created by Stas on 10.05.2017.
 */
public interface Operation<T> {
    T parseString(String number) throws ParsingException;

    T add(T a, T b) throws OverflowException;

    T mul(T a, T b) throws OverflowException;

    T div(T a, T b) throws OverflowException;

    T mod(T a, T b) throws OverflowException;

    T sub(T a, T b) throws OverflowException;

    T not(T a) throws OverflowException;

    T abs(T a) throws OverflowException;

    default T sqr(T a) throws OverflowException {
        return mul(a, a);
    }

}
