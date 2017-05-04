package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

import java.util.function.BinaryOperator;

/**
 * Created by Stas on 31.03.2017.
 */
public class BinToken<T> extends Token<T> {
    private BinaryOperator<TripleExpression<T> > factory;

    public BinToken(String name, String str, BinaryOperator<TripleExpression<T> > factory) {
        super(name, str);
        this.factory = factory;
    }

    public final TripleExpression<T> get(TripleExpression<T> first, TripleExpression<T> second) {
        return factory.apply(first, second);
    }
}
