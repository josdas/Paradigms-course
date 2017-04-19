package ru.ifmo.ctddev.naumov.parse.parse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

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