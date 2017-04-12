package ru.ifmo.ctddev.naumov.parse.levelAndParse;


import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.operator.Const;
import ru.ifmo.ctddev.naumov.parse.operator.Variable;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelParse implements AbstractLevel {
    @Override
    public TripleExpression calc(ExpressionParser parser) throws ParsingException {
        parser.nextToken();
        TripleExpression temp = null;
        if (parser.getCurToken().equalsName("number")) {
            temp = new Const(parser.getValue());
            parser.nextToken();
        } else if (parser.getCurToken().equalsName("variable")) {
            temp = new Variable(parser.getCurToken().getStr());
            parser.nextToken();
        } else if (parser.getCurToken().equalsName("leftBrace")) {
            int left = parser.getIndex();
            temp = parser.parse();
            if(!parser.getCurToken().equalsName("rightBrace")) {
                throw new ParsingException("The bracket is not closed at position: " + left
                        + "\n A closing brace is expected at position: "
                        + parser.getSubstringWithErrorEnd());
            }
            parser.nextToken();
        }
        return temp;
    }
}

