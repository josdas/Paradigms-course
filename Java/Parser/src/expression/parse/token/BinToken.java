package expression.parse.token;

import expression.parse.TripleExpression;
import expression.parse.Operators.Operation;

/**
 * Created by Stas on 31.03.2017.
 */
public class BinToken<T> extends Token<T> {
    private MyOperatorBin<T> factory;

    public BinToken(String name, String str, MyOperatorBin<T> factory) {
        super(name, str);
        this.factory = factory;
    }

    public final TripleExpression<T> get(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        return factory.apply(first, second, operation);
    }
}
