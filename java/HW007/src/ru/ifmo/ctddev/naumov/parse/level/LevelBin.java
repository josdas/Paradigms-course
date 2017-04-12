package ru.ifmo.ctddev.naumov.parse.level;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.ExpressionParser;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelBin extends AbstractLevelWithTokens {
    public LevelBin(String tokens[], AbstractLevel nextLevel) {
        super(tokens, nextLevel);
    }

    @Override
    public TripleExpression calc() throws ParsingException, OverflowException, IllegalOperationException {
        TripleExpression temp = nextLevel.calc();
        boolean used = true;
        while (used) {
            used = false;
            for (String name : tokens) {
                if (ExpressionParser.getCurToken().equalsName(name)) {
                    BinToken lastToken = (BinToken) ExpressionParser.getCurToken();
                    TripleExpression second = nextLevel.calc();
                    if (second == null) {
                        throw new ParsingException("No last argument: " + ExpressionParser.getSubstringWithErrorBegin());
                    }
                    temp = lastToken.get(temp, second);
                    used = true;
                    break;
                }
            }
        }
        return temp;
    }
}

