package ru.ifmo.ctddev.naumov.parse.levelAndParse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public interface AbstractLevel {
   TripleExpression calc(ExpressionParser parser) throws ParsingException;
}