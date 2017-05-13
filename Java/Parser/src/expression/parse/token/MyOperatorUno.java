package expression.parse.token;

import expression.parse.TripleExpression;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 10.05.2017.
 */
public interface MyOperatorUno<T> {
    TripleExpression<T> apply(TripleExpression<T> a, Operation<T> b);
}
