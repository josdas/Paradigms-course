package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Stas on 31.03.2017.
 */
public class BinToken extends Token {
    public BinToken(String name, String str, TripleExpression operator) {
        super(name, str, operator);
    }

    public final TripleExpression get(TripleExpression first, TripleExpression second) {
        try {
            return operator.getClass()
                    .getConstructor(new Class[]{TripleExpression.class, TripleExpression.class})
                    .newInstance(first, second);
        } catch (NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException
                | InstantiationException e) {
            e.printStackTrace();
            assert false;
            return null;
        }
    }
}
