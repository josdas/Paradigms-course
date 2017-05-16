package expression.parse.operators;

import expression.exception.ParsingException;

/**
 * Created by Stas on 16.05.2017.
 */
public class DoubleOperation implements Operation<Double> {
    @Override
    public Double parseString(String number) throws ParsingException {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Incorrect number");
        }
    }

    private void checkZero(Double x, String error) throws ParsingException {
        if (x == 0) {
            throw new ParsingException(error);
        }
    }

    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double mul(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double div(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double mod(Double a, Double b) {
        return a % b;
    }

    @Override
    public Double sub(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double not(Double a) {
        return -a;
    }

    @Override
    public Double abs(Double a) {
        return Math.abs(a);
    }
}
