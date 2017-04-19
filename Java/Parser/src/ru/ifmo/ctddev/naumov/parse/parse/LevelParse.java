package ru.ifmo.ctddev.naumov.parse.parse;


import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.operator.Const;
import ru.ifmo.ctddev.naumov.parse.operator.Variable;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelParse<T> implements AbstractLevel<T> {
    @Override
    public TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException {
        parser.nextToken();
        TripleExpression<T> temp = null;
        if (parser.getCurToken().equals(ExpressionParser.NUMBER)) {
            temp = new Const(parser.getValue());
            parser.nextToken();
        } else if (parser.getCurToken().equalsName("variable")) {
            temp = new Variable(parser.getCurToken().getStr());
            parser.nextToken();
        } else if (parser.getCurToken().equals(ExpressionParser.RIGHT_BRACE)) {
            int left = parser.getIndex();
            temp = parser.parse();
            if(!parser.getCurToken().equals(ExpressionParser.RIGHT_BRACE)) {
                throw new ParsingException("The bracket is not closed at position: " + left
                        + "\n A closing brace is expected at position: "
                        + parser.getSubstringWithErrorEnd());
            }
            parser.nextToken();
        }
        return temp;
    }
}

