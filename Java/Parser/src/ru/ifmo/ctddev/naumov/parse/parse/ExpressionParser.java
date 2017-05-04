package ru.ifmo.ctddev.naumov.parse.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.Parser;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.operator.*;
import ru.ifmo.ctddev.naumov.parse.token.BaseToken;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;

import java.util.ArrayList;

/**
 * Created by Stas on 28.03.2017.
 */
public class ExpressionParser<T> implements Parser<T> {
    public final BaseToken<T> VARIABLE_Z = new BaseToken<>("variable", "z");
    public final BaseToken<T> VARIABLE_Y = new BaseToken<>("variable", "y");
    public final BaseToken<T> VARIABLE_X = new BaseToken<>("variable", "x");
    public final BaseToken<T> LEFT_BRACE = new BaseToken<>("leftBrace", "(");
    public final BaseToken<T> RIGHT_BRACE = new BaseToken<>("rightBrace", ")");
    public final BaseToken<T> END = new BaseToken<>("end");
    public final BaseToken<T> START = new BaseToken<>("start");
    public final BaseToken<T> NUMBER = new BaseToken<>("number");

    public final BinToken<T> MUL = new BinToken<T>("mul", "*", CheckedMultiply::new);
    public final BinToken<T> DIV = new BinToken<T>("div", "/", CheckedDivide::new);
    public final BinToken<T> ADD = new BinToken<T>("add", "+", CheckedAdd::new);
    public final BinToken<T> SUB = new BinToken<T>("sub", "-", CheckedSubtract::new);
    public final BinToken<T> MAX = new BinToken<T>("max", "max", CheckedMax::new);
    public final BinToken<T> MIN = new BinToken<T>("min", "min", CheckedMin::new);

    public final UnaryToken<T> NEGATIVE = new UnaryToken<T>("not", "-", CheckedNegate::new);
    public final UnaryToken<T> SQRT = new UnaryToken<T>("sqrt", "sqrt", CheckedSqrt::new);
    public final UnaryToken<T> ABS = new UnaryToken<T>("abs", "abs", CheckedAbs::new);

    private ArrayList<BaseToken<T>> systemTokens;
    private ArrayList<BinToken<T>> binTokens;
    private ArrayList<UnaryToken<T>> unaryTokens;

    private final AbstractLevel<T> mainLevel =
            new LevelBin<T>(
                    new LevelBin<T>(
                            new LevelBin<T>(
                                    new LevelUno<T>(
                                            new LevelParse<T>(),
                                            NEGATIVE, SQRT, ABS),
                                    MUL, DIV
                            ),
                            SUB, ADD
                    ),
                    MIN, MAX
            );

    private String expression;
    private T value;
    private int cur;
    private int beginOfLastOperator;
    private BaseToken<T> curToken;

    public ExpressionParser() {
        systemTokens = new ArrayList<BaseToken<T>>();
        binTokens = new ArrayList<BinToken<T>>();
        unaryTokens = new ArrayList<UnaryToken<T>>();

        systemTokens.add(VARIABLE_X);
        systemTokens.add(VARIABLE_Y);
        systemTokens.add(VARIABLE_Z);
        systemTokens.add(LEFT_BRACE);
        systemTokens.add(RIGHT_BRACE);
        systemTokens.add(END);
        systemTokens.add(START);
        systemTokens.add(NUMBER);

        binTokens.add(MUL);
        binTokens.add(DIV);
        binTokens.add(ADD);
        binTokens.add(SUB);
        binTokens.add(MAX);
        binTokens.add(MIN);

        unaryTokens.add(NEGATIVE);
        unaryTokens.add(SQRT);
        unaryTokens.add(ABS);
    }

    BaseToken<T> getCurToken() {
        return curToken;
    }

    int getIndex() {
        return cur;
    }

    T getValue() {
        return value;
    }

    private boolean isUnary(BaseToken token) {
        return !token.equals(NUMBER)
                && !token.equalsName("variable")
                && !token.equals(RIGHT_BRACE);
    }

    private boolean isBinary(BaseToken token) {
        return !isUnary(token);
    }

    private boolean setToken(String name, final ArrayList<BinToken<T>> tokens) {
        for (BaseToken<T> token : tokens) {
            if (token.equalsStr(name)) {
                curToken = token;
                return true;
            }
        }
        return false;
    }

    final char operators[] = {'+', '-', '/', '*', '(', ')'};

    private boolean isOperator(char c) {
        for (char v : operators) {
            if (v == c) {
                return true;
            }
        }
        return false;
    }

    void nextToken() throws ParsingException {
        while (cur < expression.length() && Character.isWhitespace(expression.charAt(cur))) {
            cur++;
        }
        if (cur >= expression.length()) {
            curToken = END;
            return;
        }
        char chr = expression.charAt(cur);
        if (Character.isDigit(chr)
                || chr == '-'
                && cur + 1 < expression.length()
                && Character.isDigit(expression.charAt(cur + 1))
                && isUnary(curToken)) {
            curToken = NUMBER;
            int left = cur;
            do {
                cur++;
            } while (cur < expression.length() && Character.isDigit(expression.charAt(cur)));
            try {
                value = parseNumber(expression.substring(left, cur));
            } catch (NumberFormatException e) {
                throw new OverflowException("Too big number begin at position: " + getSubstringWithError(left));
            }
            return;
        }
        if (chr == '-' && isUnary(curToken)) {
            curToken = NEGATIVE;
            cur += 1;
            return;
        }
        String temp;
        beginOfLastOperator = cur;
        if (Character.isLetter(chr)) {
            do {
                char curChar = expression.charAt(cur);
                if (!Character.isLetterOrDigit(curChar) && !isOperator(curChar)) {
                    throw new IllegalOperationException("Unknown character: " + getSubstringWithError(cur));
                }
                cur++;
            } while (cur < expression.length() && Character.isLetterOrDigit(expression.charAt(cur)));
            temp = expression.substring(beginOfLastOperator, cur);
        } else {
            temp = Character.toString(chr);
            if (!Character.isLetterOrDigit(chr) && !isOperator(chr)) {
                throw new IllegalOperationException("Unknown character: " + getSubstringWithError(cur));
            }
            cur++;
        }
        boolean isBinaryLastToken = isBinary(curToken);
        if (setToken(temp, binTokens)) {
            if (!isBinaryLastToken) {
                throw new ParsingException("No first argument: " + getSubstringWithErrorBegin());
            }
            return;
        }
        if (setToken(temp, unaryTokens)) {
            return;
        }
        if (setToken(temp, systemTokens)) {
            return;
        }
        throw new IllegalOperationException("Unknown function: " + getSubstringWithErrorBegin());

    }

    TripleExpression<T> parse() throws ParsingException {
        return mainLevel.calc(this);
    }

    final int shiftForPrint = 10;

    private String getSubstringWithError(int pos) {
        int left = pos - shiftForPrint;
        int right = pos + shiftForPrint;
        if (left < 0) {
            left = 0;
        }
        if (right > expression.length()) {
            right = expression.length();
        }
        String temp = "";
        for (int i = left; i < right; i++) {
            if (i == pos) {
                temp += "^";
            } else {
                temp += " ";
            }
        }
        return (pos + 1) + "\n" + expression.substring(left, right) + "\n" + temp;
    }

    String getSubstringWithErrorEnd() {
        return getSubstringWithError(cur - 1);
    }

    String getSubstringWithErrorBegin() {
        return getSubstringWithError(beginOfLastOperator);
    }

    public TripleExpression<T> parse(String expression) throws ParsingException {
        cur = 0;
        curToken = START;
        this.expression = expression;
        TripleExpression<T> temp = parse();
        if (!getCurToken().equals(END)) {
            if (getCurToken().equals(RIGHT_BRACE)) {
                throw new ParsingException("No opening parenthesis: " + getSubstringWithErrorEnd());
            } else {
                throw new ParsingException("Syntax error: " + getSubstringWithErrorEnd());
            }
        }
        return temp;
    }
}