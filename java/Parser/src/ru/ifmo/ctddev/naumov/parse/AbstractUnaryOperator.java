package ru.ifmo.ctddev.naumov.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractUnaryOperator<T> implements TripleExpression<T> {
    TripleExpression<T> first;

    protected abstract T operator(T x) throws OverflowException, IllegalOperationException;

    public AbstractUnaryOperator(TripleExpression<T> t) {
        first = t;
    }

    public T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException {
        return operator(first.evaluate(x, y, z));
    }
}
