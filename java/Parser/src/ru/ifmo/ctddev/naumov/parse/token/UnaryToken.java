package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

import java.util.function.UnaryOperator;

/**
 * Created by Stas on 31.03.2017.
 */
public class UnaryToken extends Token {
    protected UnaryOperator<TripleExpression> factory;

    public UnaryToken(String name, String str, UnaryOperator<TripleExpression> factory) {
        super(name, str);
        this.factory = factory;
    }

    public final TripleExpression get(TripleExpression first) {
        return factory.apply(first);
    }

}
