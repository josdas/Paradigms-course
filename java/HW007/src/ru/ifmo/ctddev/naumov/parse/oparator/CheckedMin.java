package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.parse.AbstractBinaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedMin extends AbstractBinaryOperator {
    public CheckedMin(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    public CheckedMin() {

    }

    protected int operator(int a, int b) throws OverflowException {
        if (a > b) {
            return b;
        }
        return a;
    }
}
