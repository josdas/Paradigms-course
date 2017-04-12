package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.parse.AbstractBinaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedDivide extends AbstractBinaryOperator {
    public CheckedDivide(TripleExpression a, TripleExpression b) {
        super(a, b);
    }

    public CheckedDivide() {

    }

    private void check(int x, int y) throws IllegalOperationException, OverflowException {
        if (y == 0) {
            throw new IllegalOperationException("Division by zero");
        }
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    protected int operator(int a, int b) throws IllegalOperationException, OverflowException {
        check(a, b);
        return a / b;
    }
}
