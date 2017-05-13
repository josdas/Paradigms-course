package expression.parse;

import expression.exception.ParsingException;

/**
 * Created by Stas on 28.03.2017.
 */
public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException;
}