package ru.ifmo.ctddev.naumov.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractUnaryOperator implements TripleExpression {
    TripleExpression first;

    protected abstract int operator(int x) throws OverflowException, IllegalOperationException;

    public AbstractUnaryOperator(TripleExpression t) {
        first = t;
    }

    public AbstractUnaryOperator() {
    }

    public int evaluate(int x, int y, int z) throws OverflowException, IllegalOperationException {
        return operator(first.evaluate(x, y, z));
    }
}
