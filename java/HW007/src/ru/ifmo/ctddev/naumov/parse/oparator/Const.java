package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class Const implements TripleExpression {
    private int value;

    public Const(int x) {
        value = x;
    }

    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}
