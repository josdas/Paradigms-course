package ru.ifmo.ctddev.naumov.parse.parse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelBin<T> extends AbstractLevelWithTokens<T> {
    BinToken<T> tokens[];

    @SafeVarargs
    public LevelBin(AbstractLevel<T> nextLevel, BinToken<T> ... tokens) {
        super(nextLevel);
        this.tokens = tokens;
    }

    @Override
    public TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException {
        TripleExpression<T> temp = nextLevel.calc(parser);
        outer: while (true) {
            for (BinToken<T> token : tokens) {
                if (parser.getCurToken().equals(token)) {
                    BinToken<T> lastToken = (BinToken<T>) parser.getCurToken();
                    TripleExpression<T> second = nextLevel.calc(parser);
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

