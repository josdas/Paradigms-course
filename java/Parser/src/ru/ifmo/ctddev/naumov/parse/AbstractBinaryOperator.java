package ru.ifmo.ctddev.naumov.parse;


import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public abstract class AbstractBinaryOperator<T> implements TripleExpression<T> {
    TripleExpression<T> first, second;

    public AbstractBinaryOperator(TripleExpression<T> a, TripleExpression<T> b) {
        first = a;
        second = b;
    }

    protected abstract T operator(T a, T b) throws OverflowException, IllegalOperationException;

    public T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException {
        return operator(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
