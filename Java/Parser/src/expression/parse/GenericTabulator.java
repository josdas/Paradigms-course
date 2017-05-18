package expression.parse;

import expression.exception.ParsingException;
import expression.parse.operators.*;
import expression.parse.parse.ExpressionParser;

import java.util.HashMap;

/**
 * Created by Stas on 10.05.2017.
 */
public class GenericTabulator implements Tabulator {
    private final static HashMap<String, Operation<?>> MODES = new HashMap<>();

    static {
        MODES.put("i", new IntegerOperation(true));
        MODES.put("u", new IntegerOperation(false));
        MODES.put("bi", new BigIntegerOperation());
        MODES.put("b", new ByteOperation());
        MODES.put("d", new DoubleOperation());
        MODES.put("f", new FloatOperation());
    }

    private Operation<?> getOperation(final String mode) {
        return MODES.get(mode);
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        mode = "d";
        return makeTable(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] makeTable(Operation<T> op, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<>(op);
        TripleExpression<T> exp;
        try {
            exp = parser.parse(expression);
        } catch (Exception e) {
            return res;
        }
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        res[i - x1][j - y1][k - z1] = exp.evaluate(
                                op.parseString(Integer.toString(i)),
                                op.parseString(Integer.toString(j)),
                                op.parseString(Integer.toString(k))
                        );
                    } catch (ParsingException e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }
}