package ru.ifmo.ctddev.naumov.parse.level;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public interface Level {
    TripleExpression calc() throws ParsingException, OverflowException, IllegalOperationException;
}
