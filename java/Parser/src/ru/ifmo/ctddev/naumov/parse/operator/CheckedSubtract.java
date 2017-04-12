package ru.ifmo.ctddev.naumov.parse.operator;

import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.parse.AbstractBinaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedSubtract extends AbstractBinaryOperator {
    public CheckedSubtract(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    private void check(int x, int y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }

    protected int operator(int a, int b) throws OverflowException {
        check(a, b);
        return a - b;
    }
}
