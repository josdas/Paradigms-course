package expression.parse.operators;

import expression.exception.ParsingException;

/**
 * Created by Stas on 16.05.2017.
 */
public class FloatOperation implements Operation<Float> {
    @Override
    public Float parseString(String number) throws ParsingException {
        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Incorrect number");
        }
    }

    private void checkZero(float x, String error) throws ParsingException {
        if (x == 0) {
            throw new ParsingException(error);
        }
    }

    @Override
    public Float add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float mul(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float div(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float mod(Float a, Float b) {
        return a % b;
    }

    @Override
    public Float sub(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float not(Float a) {
        return -a;
    }

    @Override
    public Float abs(Float a) {
        return Math.abs(a);
    }
}
