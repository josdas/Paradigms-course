package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

import java.util.function.BinaryOperator;

/**
 * Created by Stas on 31.03.2017.
 */
public class BinToken extends Token {
    private BinaryOperator<TripleExpression> factory;

    public BinToken(String name, String str, BinaryOperator<TripleExpression> factory) {
        super(name, str);
        this.factory = factory;
    }

    public final TripleExpression get(TripleExpression first, TripleExpression second) {
        return factory.apply(first, second);
    }
}
