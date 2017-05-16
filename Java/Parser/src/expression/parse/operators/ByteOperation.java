package expression.parse.operators;

import expression.exception.ParsingException;

/**
 * Created by Stas on 16.05.2017.
 */
public class ByteOperation implements Operation<Byte> {
    @Override
    public Byte parseString(String number) throws ParsingException {
        try {
            return Byte.parseByte(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Incorrect number");
        }
    }

    private void checkZero(int x, String error) throws ParsingException {
        if (x == 0) {
            throw new ParsingException(error);
        }
    }

    @Override
    public Byte add(Byte a, Byte b) {
        return (byte) (a + b);
    }

    @Override
    public Byte mul(Byte a, Byte b) {
        return (byte) (a * b);
    }

    @Override
    public Byte div(Byte a, Byte b) throws ParsingException {
        checkZero(b, "Division by zero");
        return (byte) (a / b);
    }

    @Override
    public Byte mod(Byte a, Byte b) throws ParsingException {
        checkZero(b, "Module by zero");
        return (byte) (a % b);
    }

    @Override
    public Byte sub(Byte a, Byte b) {
        return (byte) (a - b);
    }

    @Override
    public Byte not(Byte a) {
        return (byte) -a;
    }

    @Override
    public Byte abs(Byte a) {
        return (byte) Math.abs(a);
    }
}
