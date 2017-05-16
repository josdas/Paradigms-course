package expression.parse.operators;

import expression.exception.OverflowException;
import expression.exception.ParsingException;

/**
 * Created by Stas on 16.05.2017.
 */
public class IntegerOperation implements Operation<Integer> {
    private final boolean isCheck;

    public IntegerOperation(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public Integer parseString(String number) throws ParsingException {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new ParsingException("Incorrect number");
        }
    }

    private void checkAdd(int x, int y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new OverflowException();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException();
        }
    }

    private void checkMul(int x, int y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new OverflowException();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new OverflowException();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new OverflowException();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException();
        }
    }

    private void checkSub(int x, int y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new OverflowException();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException();
        }
    }


    private void checkDiv(int x, int y) throws OverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }

    private void checkZero(int x, String error) throws ParsingException {
        if (x == 0) {
            throw new ParsingException(error);
        }
    }

    private void checkNot(final Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
    }

    @Override
    public Integer add(Integer a, Integer b) throws OverflowException {
        if (isCheck) {
            checkAdd(a, b);
        }
        return a + b;
    }

    @Override
    public Integer mul(Integer a, Integer b) throws OverflowException {
        if (isCheck) {
            checkMul(a, b);
        }
        return a * b;
    }

    @Override
    public Integer div(Integer a, Integer b) throws ParsingException {
        checkZero(b, "Division by zero");
        if (isCheck) {
            checkDiv(a, b);
        }
        return a / b;
    }

    @Override
    public Integer mod(Integer a, Integer b) throws ParsingException {
        checkZero(b, "Module by zero");
        if (isCheck) {
            checkDiv(a, b);
        }
        return a % b;
    }

    @Override
    public Integer sub(Integer a, Integer b) throws OverflowException {
        if(isCheck) {
            checkSub(a, b);
        }
        return a - b;
    }

    @Override
    public Integer not(Integer a) throws OverflowException {
        if (isCheck) {
            checkNot(a);
        }
        return -a;
    }

    @Override
    public Integer abs(Integer a) throws OverflowException {
        if (isCheck) {
            checkNot(a);
        }
        return Math.abs(a);
    }
}
