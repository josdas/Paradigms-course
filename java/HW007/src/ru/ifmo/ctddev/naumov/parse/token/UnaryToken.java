package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Stas on 31.03.2017.
 */
public class UnaryToken extends Token {
    public UnaryToken(String name, String str, TripleExpression operator) {
        super(name, str, operator);
    }

    public UnaryToken(String name, String str) {
        super(name, str);
    }

    public UnaryToken(String name) {
        super(name);
    }

    public final TripleExpression get(TripleExpression first) {
        try {
            return operator.getClass()
                    .getConstructor(new Class[]{TripleExpression.class})
                    .newInstance(first);
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
