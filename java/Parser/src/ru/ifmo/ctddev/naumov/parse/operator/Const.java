package ru.ifmo.ctddev.naumov.parse.operator;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class Const<T> implements TripleExpression<T> {
    private T value;

    public Const(T x) {
        value = x;
    }

    public T evaluate(T x, T y, T z) {
        return value;
    }
}
