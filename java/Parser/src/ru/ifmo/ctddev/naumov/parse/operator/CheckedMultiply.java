package ru.ifmo.ctddev.naumov.parse.operator;

import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.parse.AbstractBinaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedMultiply extends AbstractBinaryOperator {
    public CheckedMultiply(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    private void check(int x, int y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    protected int operator(int a, int b) throws OverflowException {
        check(a, b);
        return a * b;
    }
}
