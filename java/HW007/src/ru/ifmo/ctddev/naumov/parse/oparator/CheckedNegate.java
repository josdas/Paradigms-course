package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.exception.*;
import ru.ifmo.ctddev.naumov.parse.AbstractUnaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedNegate extends AbstractUnaryOperator {
    public CheckedNegate(TripleExpression t) {
        super(t);
    }

    public CheckedNegate() {

    }

    private void check(int x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    protected int operator(int x) throws OverflowException {
        check(x);
        return -x;
    }
}
