package ru.ifmo.ctddev.naumov.parse.level;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.ExpressionParser;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public abstract class AbstractLevelWithTokens extends AbstractLevel {
    String tokens[];
    AbstractLevel nextLevel;

    public abstract TripleExpression calc() throws ParsingException, OverflowException, IllegalOperationException;

    AbstractLevelWithTokens(String tokens[], AbstractLevel nextLevel) {
        if (tokens != null) {
            for (String token : tokens) {
                assert !(ExpressionParser.findBinToken(token) == null
                        && ExpressionParser.findUnaryToken(token) == null);
            }
        }
        this.tokens = tokens;
        this.nextLevel = nextLevel;
    }
}