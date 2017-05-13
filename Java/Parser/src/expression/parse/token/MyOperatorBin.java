package expression.parse.token;

import expression.parse.TripleExpression;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 10.05.2017.
 */
public interface MyOperatorBin<T> {
    TripleExpression<T> apply(TripleExpression<T> a, TripleExpression<T> b, Operation<T> c);
}
