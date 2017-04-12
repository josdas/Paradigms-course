package ru.ifmo.ctddev.naumov.parse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.level.AbstractLevel;
import ru.ifmo.ctddev.naumov.parse.level.LevelBin;
import ru.ifmo.ctddev.naumov.parse.level.LevelParse;
import ru.ifmo.ctddev.naumov.parse.level.LevelUno;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;
import ru.ifmo.ctddev.naumov.parse.token.Token;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;
import ru.ifmo.ctddev.naumov.parse.oparator.*;

/**
 * Created by Stas on 28.03.2017.
 */
public class ExpressionParser implements Parser {
    private static String expression;
    private static long value;

    private static int cur;
    private static Token curToken;

    final static BinToken binTokens[] = {
            new BinToken("mul", "*", new CheckedMultiply()),
            new BinToken("div", "/", new CheckedDivide()),
            new BinToken("add", "+", new CheckedAdd()),
            new BinToken("sub", "-", new CheckedSubtract()),
            new BinToken("max", "max", new CheckedMax()),
            new BinToken("min", "min", new CheckedMin()),
    };

    final static UnaryToken unaryTokens[] = {
            new UnaryToken("number"),
            new UnaryToken("start"),
            new UnaryToken("end"),
            new UnaryToken("leftBrace", "("),
            new UnaryToken("rightBrace", ")"),

            new UnaryToken("variable", "x"),
            new UnaryToken("variable", "y"),
            new UnaryToken("variable", "z"),

            new UnaryToken("not", "-", new CheckedNegate()),
            new UnaryToken("sqrt", "sqrt", new CheckedSqrt()),
            new UnaryToken("abs", "abs", new CheckedAbs()),
    };


    public static Token getCurToken() {
        return curToken;
    }

    public static long getValue() {
        return value;
    }

    private static boolean isUnary(Token token) {
        return !token.equalsName("number") && !token.equalsName("variable") && !token.equalsName("rightBrace");
    }

    private static boolean isBinary(Token token) {
        return token.equalsName("number") || token.equalsName("variable") || token.equalsName("rightBrace");
    }

    public static Token findToken(String name, final Token tokens[]) {
        for (Token token : tokens) {
            if (token.equalsName(name)) {
                return token;
            }
        }
        return null;
    }

    public static Token findBinToken(String name) {
        return findToken(name, binTokens);
    }

    public static Token findUnaryToken(String name) {
        return findToken(name, unaryTokens);
    }

    private static boolean setToken(String name, final Token tokens[]) {
        for (Token token : tokens) {
            if (token.equalsStr(name)) {
                curToken = token;
                return true;
            }
        }
        return false;
    }

    private static int beginOfLastOperator;

    public static void nextToken() throws ParsingException, IllegalOperationException, OverflowException {
        while (cur < expression.length() && Character.isSpace(expression.charAt(cur))) {
            cur++;
        }
        if (cur >= expression.length()) {
            curToken = findUnaryToken("end");
            return;
        }
        char chr = expression.charAt(cur);
        if (Character.isDigit(chr)
                || chr == '-'
                && cur + 1 < expression.length()
                && Character.isDigit(expression.charAt(cur + 1))
                && isUnary(curToken)) {
            curToken = findUnaryToken("number");
            int left = cur;
            do {
                cur++;
            } while (cur < expression.length() && Character.isDigit(expression.charAt(cur)));
            try {
                value = Integer.parseInt(expression.substring(left, cur));
            } catch (NumberFormatException e) {
                throw new OverflowException("Too big int begin at position: " + getSubstringWithError(left));
            }
            return;
        }
        if (chr == '-' && isUnary(curToken)) {
            curToken = findUnaryToken("not");
            cur += 1;
            return;
        }
        String temp;
        beginOfLastOperator = cur;
        if (Character.isLetter(chr)) { // идинтификатор
            do {
                cur++;
            } while (cur < expression.length() && Character.isLetterOrDigit(expression.charAt(cur)));
            temp = expression.substring(beginOfLastOperator, cur);
        } else { // знак
            temp = chr + "";
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
        throw new IllegalOperationException("Unknown operator: " + getSubstringWithErrorBegin());

    }

    private static AbstractLevel mainLevel;

    public static TripleExpression parse() throws ParsingException, OverflowException, IllegalOperationException {
        return mainLevel.calc();
    }

    private void init() {
        cur = 0;
        value = 0;
        curToken = findUnaryToken("start");
    }

    final static int shiftForPrint = 10;

    private static String getSubstringWithError(int pos) {
        int left = pos - shiftForPrint;
        if (left < 0) {
            left = 0;
        }
        int right = pos + shiftForPrint;
        if (right > expression.length()) {
            right = expression.length();
        }
        String temp = "";
        for (int i = left; i < right; i++) {
            if (i == pos) {
                temp += "^";
            } else {
                temp += "~";
            }
        }
        return pos + "\n" + expression.substring(left, right) + "\n" + temp;
    }

    public static String getSubstringWithErrorEnd() {
        return getSubstringWithError(cur - 1);
    }

    public static String getSubstringWithErrorBegin() {
        return getSubstringWithError(beginOfLastOperator);
    }

    public TripleExpression parse(String expression)
            throws ParsingException, OverflowException, IllegalOperationException {
        init();
        ExpressionParser.expression = expression;
        mainLevel =
                new LevelBin(new String[]{"min", "max"},
                new LevelBin(new String[]{"sub", "add"},
                new LevelBin(new String[]{"mul", "div"},
                new LevelUno(new String[]{"not", "sqrt", "abs"},
                new LevelParse()))));
        TripleExpression temp = parse();
        if (!getCurToken().equalsName("end")) {
            if (getCurToken().equalsName("rightBrace")) {
                throw new ParsingException("No opening parenthesis: " + getSubstringWithErrorEnd());
            } else {
                throw new ParsingException("Syntax error: " + getSubstringWithErrorEnd());
            }
        }
        return temp;
    }
}