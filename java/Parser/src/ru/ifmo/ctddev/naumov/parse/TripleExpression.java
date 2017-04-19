package ru.ifmo.ctddev.naumov.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public interface TripleExpression<T> {
    T evaluate(T x, T y, T z) throws OverflowException, IllegalOperationException;
}
