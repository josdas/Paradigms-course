package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.parse.AbstractUnaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedAbs extends AbstractUnaryOperator {
    public CheckedAbs(TripleExpression t) {
        super(t);
    }

    public CheckedAbs() {

    }

    private void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int operator(int x) throws OverflowException {
        check(x);
        if (x < 0) {
            return -x;
        }
        return x;
    }
}
