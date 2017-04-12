package ru.ifmo.ctddev.naumov.parse.levelAndParse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelUno extends AbstractLevelWithTokens {
    public LevelUno(String tokens[], AbstractLevel nextLevel) {
        super(tokens, nextLevel);
    }

    @Override
    public TripleExpression calc(ExpressionParser parser) throws ParsingException {
        TripleExpression temp = nextLevel.calc(parser);
        for (String name : tokens) {
            if (parser.getCurToken().equalsName(name)) {
                UnaryToken lastToken = (UnaryToken) parser.getCurToken();
                TripleExpression value = calc(parser);
                if (value == null) {
                    throw new ParsingException("No argument: "
                            + parser.getSubstringWithErrorBegin());
                }
                temp = lastToken.get(value);
                break;
            }
        }
        return temp;
    }
}