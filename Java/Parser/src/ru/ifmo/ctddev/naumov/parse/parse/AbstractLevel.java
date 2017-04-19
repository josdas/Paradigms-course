package ru.ifmo.ctddev.naumov.parse.parse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public interface AbstractLevel<T> {
   TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException;
}