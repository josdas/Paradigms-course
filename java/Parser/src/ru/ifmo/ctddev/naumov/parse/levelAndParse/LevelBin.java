package ru.ifmo.ctddev.naumov.parse.levelAndParse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelBin extends AbstractLevelWithTokens {
    public LevelBin(String[] tokens, AbstractLevel nextLevel) {
        super(tokens, nextLevel);
    }

    @Override
    public TripleExpression calc(ExpressionParser parser) throws ParsingException {
        TripleExpression temp = nextLevel.calc(parser);
        outer: while (true) {
            for (String name : tokens) {
                if (parser.getCurToken().equalsName(name)) {
                    BinToken lastToken = (BinToken) parser.getCurToken();
                    TripleExpression second = nextLevel.calc(parser);
                    if (second == null) {
                        throw new ParsingException("No last argument: " + parser.getSubstringWithErrorBegin());
                    }
                    temp = lastToken.get(temp, second);
                    continue outer;
                }
            }
            break;
        }
        return temp;
    }
}

