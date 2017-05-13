package expression.parse.parse;

import expression.exception.ParsingException;
import expression.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public interface AbstractLevel<T> {
   TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException;
}