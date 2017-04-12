package ru.ifmo.ctddev.naumov.parse.oparator;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.parse.AbstractUnaryOperator;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class CheckedSqrt extends AbstractUnaryOperator {
    public CheckedSqrt(TripleExpression t) {
        super(t);
    }

    public CheckedSqrt() {

    }

    private void check(int x) throws OverflowException, IllegalOperationException {
        if (x < 0) {
            throw new IllegalOperationException("Square root from a negative number");
        }
    }

    double eps = 1e-7;

    protected int operator(int x) throws OverflowException, IllegalOperationException {
        check(x);
        if (x == 0) {
            return 0;
        }
        double result = 1;
        while (true) {
            double nx = (result + x / result) / 2;
            if (result - nx > -eps && result - nx < eps) {
                break;
            }
            result = nx;
        }
        int temp = (int) (result + 0.5);
        temp -= 2;
        if (temp < 0) {
            temp = 0;
        }
        while (temp * temp <= x) {
            temp++;
        }
        return temp - 1;
    }
}
