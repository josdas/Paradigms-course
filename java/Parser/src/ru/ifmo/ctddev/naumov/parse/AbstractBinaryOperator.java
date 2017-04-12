package ru.ifmo.ctddev.naumov.parse;


import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractBinaryOperator implements TripleExpression {
    TripleExpression first, second;

    public AbstractBinaryOperator(TripleExpression a, TripleExpression b) {
        first = a;
        second = b;
    }

    protected abstract int operator(int a, int b) throws OverflowException, IllegalOperationException;

    public int evaluate(int x, int y, int z) throws OverflowException, IllegalOperationException {
        return operator(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
