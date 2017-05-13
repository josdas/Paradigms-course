package expression.parse.parse;


import expression.exception.ParsingException;
import expression.parse.TripleExpression;
import expression.parse.operator.Const;
import expression.Variable;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelParse<T> implements AbstractLevel<T> {
    @Override
    public TripleExpression<T> calc(ExpressionParser<T> parser) throws ParsingException {
        parser.nextToken();
        TripleExpression<T> temp = null;
        if (parser.getCurToken().equals(parser.NUMBER)) {
            temp = new Const<T>(parser.getValue());
            parser.nextToken();
        } else if (parser.getCurToken().equalsName("variable")) {
            temp = new Variable<T>(parser.getCurToken().getStr());
            parser.nextToken();
        } else if (parser.getCurToken().equals(parser.RIGHT_BRACE)) {
            int left = parser.getIndex();
            temp = parser.parse();
            if(!parser.getCurToken().equals(parser.RIGHT_BRACE)) {
                throw new ParsingException("The bracket is not closed at position: " + left
                        + "\n A closing brace is expected at position: "
                        + parser.getSubstringWithErrorEnd());
            }
            parser.nextToken();
        }
        return temp;
    }
}

