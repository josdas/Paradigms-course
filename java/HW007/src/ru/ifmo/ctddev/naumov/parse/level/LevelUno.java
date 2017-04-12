package ru.ifmo.ctddev.naumov.parse.level;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.ExpressionParser;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelUno extends AbstractLevelWithTokens {
    public LevelUno(String tokens[], AbstractLevel nextLevel) {
        super(tokens, nextLevel);
    }

    @Override
    public TripleExpression calc() throws ParsingException, OverflowException, IllegalOperationException {
        TripleExpression temp = nextLevel.calc();
        for (String name : tokens) {
            if (ExpressionParser.getCurToken().equalsName(name)) {
                UnaryToken lastToken = (UnaryToken) ExpressionParser.getCurToken();
                TripleExpression value = calc();
                if (value == null) {
                    throw new ParsingException("No argument: "
                            + ExpressionParser.getSubstringWithErrorBegin());
                }
                temp = lastToken.get(value);
                break;
            }
        }
        return temp;
    }
}