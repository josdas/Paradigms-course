package ru.ifmo.ctddev.naumov.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;

/**
 * Created by Stas on 28.03.2017.
 */
public interface TripleExpression {
    int evaluate(int x, int y, int z) throws OverflowException, IllegalOperationException;
}
