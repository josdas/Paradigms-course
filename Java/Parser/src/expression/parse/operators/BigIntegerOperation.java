package expression.parse.operators;

import expression.exception.ParsingException;

import java.math.BigInteger;

/**
 * Created by Stas on 16.05.2017.
 */
public class BigIntegerOperation implements Operation<BigInteger> {
    @Override
    public BigInteger parseString(String number) throws ParsingException {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Incorrect number");
        }
    }

    private void checkZero(BigInteger x, String error) throws ParsingException {
        if (x.equals(BigInteger.ZERO)) {
            throw new ParsingException(error);
        }
    }

    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger mul(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger div(BigInteger a, BigInteger b) throws ParsingException {
        checkZero(b, "Division by zero");
        return a.divide(b);
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) throws ParsingException {
        checkZero(b, "Module by zero");
        return a.mod(b);
    }

    @Override
    public BigInteger sub(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger not(BigInteger a) {
        return a.not();
    }

    @Override
    public BigInteger abs(BigInteger a) {
        return a.abs();
    }
}
