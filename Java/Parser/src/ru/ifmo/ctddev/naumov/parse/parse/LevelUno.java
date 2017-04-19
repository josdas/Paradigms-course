package ru.ifmo.ctddev.naumov.parse.parse;

import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelUno<T> extends AbstractLevelWithTokens<T> {
    UnaryToken<T> tokens[];
    @SafeVarargs
    public LevelUno(AbstractLevel<T> nextLevel, UnaryToken<T> ... tokens) {
        super(nextLevel);
        this.tokens = tokens;
    }

    @Override
    public TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException {
        TripleExpression<T> temp = nextLevel.calc(parser);
        for (UnaryToken<T> token : tokens) {
            if (parser.getCurToken().equals(token)) {
                UnaryToken<T> lastToken = (UnaryToken<T>) parser.getCurToken();
                TripleExpression<T> value = calc(parser);
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