package ru.ifmo.ctddev.naumov.parse.level;


import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.ExpressionParser;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.oparator.*;

/**
 * Created by Stas on 31.03.2017.
 */
public class LevelParse extends AbstractLevel {
    @Override
    public TripleExpression calc() throws ParsingException, OverflowException, IllegalOperationException {
        ExpressionParser.nextToken();
        TripleExpression temp = null;
        if(ExpressionParser.getCurToken().equalsName("number")) {
            temp = new Const((int) ExpressionParser.getValue());
            ExpressionParser.nextToken();
        } else if(ExpressionParser.getCurToken().equalsName("variable")) {
            temp = new Variable(ExpressionParser.getCurToken().getStr());
            ExpressionParser.nextToken();
        } else if(ExpressionParser.getCurToken().equalsName("leftBrace")) {
            temp = ExpressionParser.parse();
            if(!ExpressionParser.getCurToken().equalsName("rightBrace")) {
                throw new ParsingException("A closing brace is expected at position: " + ExpressionParser.getSubstringWithErrorEnd());
            }
            ExpressionParser.nextToken();
        }
        return temp;
    }
}

