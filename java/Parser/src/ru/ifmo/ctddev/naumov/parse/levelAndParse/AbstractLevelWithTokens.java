package ru.ifmo.ctddev.naumov.parse.levelAndParse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public abstract class AbstractLevelWithTokens implements AbstractLevel {
    String tokens[];
    AbstractLevel nextLevel;

    public abstract TripleExpression calc(ExpressionParser parser)
            throws ParsingException;

    AbstractLevelWithTokens(String tokens[], AbstractLevel nextLevel) {
        this.tokens = tokens;
        this.nextLevel = nextLevel;
    }
}