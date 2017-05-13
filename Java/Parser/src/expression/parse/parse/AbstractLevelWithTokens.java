package expression.parse.parse;

import expression.exception.ParsingException;
import expression.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public abstract class AbstractLevelWithTokens<T> implements AbstractLevel<T> {
    AbstractLevel<T> nextLevel;

    public abstract TripleExpression<T> calc(ExpressionParser<T> parser)
            throws ParsingException;

    AbstractLevelWithTokens(AbstractLevel<T> nextLevel) {
        this.nextLevel = nextLevel;
    }
}