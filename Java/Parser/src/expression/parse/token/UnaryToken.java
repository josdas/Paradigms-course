package expression.parse.token;

import expression.parse.TripleExpression;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 31.03.2017.
 */
public class UnaryToken<T> extends Token<T> {
    protected MyOperatorUno<T> factory;

    public UnaryToken(String name, String str, MyOperatorUno<T> factory) {
        super(name, str);
        this.factory = factory;
    }

    public final TripleExpression<T> get(TripleExpression<T> first, Operation<T> operation) {
        return factory.apply(first, operation);
    }

}
