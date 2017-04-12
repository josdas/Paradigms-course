package ru.ifmo.ctddev.naumov.parse.levelAndParse;

import ru.ifmo.ctddev.naumov.exception.IllegalOperationException;
import ru.ifmo.ctddev.naumov.exception.OverflowException;
import ru.ifmo.ctddev.naumov.exception.ParsingException;
import ru.ifmo.ctddev.naumov.parse.Parser;
import ru.ifmo.ctddev.naumov.parse.TripleExpression;
import ru.ifmo.ctddev.naumov.parse.operator.*;
import ru.ifmo.ctddev.naumov.parse.token.BaseToken;
import ru.ifmo.ctddev.naumov.parse.token.BinToken;
import ru.ifmo.ctddev.naumov.parse.token.UnaryToken;

/**
 * Created by Stas on 28.03.2017.
 */
public class ExpressionParser implements Parser {
    private final BaseToken systemTokens[] = {
            new BaseToken("number"),
            new BaseToken("start"),
            new BaseToken("end"),
            new BaseToken("leftBrace", "("),
            new BaseToken("rightBrace", ")"),
            new BaseToken("variable", "x"),
            new BaseToken("variable", "y"),
            new BaseToken("variable", "z"),
    };

    private final BinToken binTokens[] = {
            new BinToken("mul", "*", CheckedMultiply::new),
            new BinToken("div", "/", CheckedDivide::new),
            new BinToken("add", "+", CheckedAdd::new),
            new BinToken("sub", "-", CheckedSubtract::new),
            new BinToken("max", "max", CheckedMax::new),
            new BinToken("min", "min", CheckedMin::new),
    };

    private final UnaryToken unaryTokens[] = {
            new UnaryToken("not", "-", CheckedNegate::new),
            new UnaryToken("sqrt", "sqrt", CheckedSqrt::new),
            new UnaryToken("abs", "abs", CheckedAbs::new),
    };

    private final AbstractLevel mainLevel =
            new LevelBin(new String[]{"min", "max"},
            new LevelBin(new String[]{"sub", "add"},
            new LevelBin(new String[]{"mul", "div"},
            new LevelUno(new String[]{"not", "sqrt", "abs"},
            new LevelParse()))));

    private String expression;
    private int value;
    private int cur;
    private BaseToken curToken;
    private int beginOfLastOperator;

    BaseToken getCurToken() {
        return curToken;
    }

    int getIndex() {
        return cur;
    }
    int getValue() {
        return value;
    }

    private boolean isUnary(BaseToken token) {
        return !token.equalsName("number")
                && !token.equalsName("variable")
                && !token.equalsName("rightBrace");
    }

    private boolean isBinary(BaseToken token) {
        return !isUnary(token);
    }

    private BaseToken findToken(String name, final BaseToken tokens[]) {
        for (BaseToken token : tokens) {
            if (token.equalsName(name)) {
                return token;
            }
        }
        return null;
    }

    private BaseToken findUnaryToken(String name) {
        return findToken(name, unaryTokens);
    }

    private BaseToken findSystemToken(String name) {
        return findToken(name, systemTokens);
    }

    private boolean setToken(String name, final BaseToken tokens[]) {
        for (BaseToken token : tokens) {
            if (token.equalsStr(name)) {
                curToken = token;
                return true;
            }
        }
        return false;
    }

    final char operators[] = {'+', '-', '/', '*', '(', ')'};
    private boolean isOperator(char c) {
        for(char v : operators) {
            if(v == c) {
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
            curToken = findSystemToken("end");
            return;
        }
        char chr = expression.charAt(cur);
        if (Character.isDigit(chr)
                || chr == '-'
                && cur + 1 < expression.length()
                && Character.isDigit(expression.charAt(cur + 1))
                && isUnary(curToken)) {
            curToken = findSystemToken("number");
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
        if (Character.isLetter(chr)) {
            do {
                char curChar = expression.charAt(cur);
                if(!Character.isLetterOrDigit(curChar) && !isOperator(curChar)) {
                    throw new IllegalOperationException("Unknown character: " + getSubstringWithError(cur));
                }
                cur++;
            } while (cur < expression.length() && Character.isLetterOrDigit(expression.charAt(cur)));
            temp = expression.substring(beginOfLastOperator, cur);
        } else {
            temp = Character.toString(chr);
            if(!Character.isLetterOrDigit(chr) && !isOperator(chr)) {
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

    TripleExpression parse() throws ParsingException {
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

    public TripleExpression parse(String expression) throws ParsingException {
        cur = 0;
        value = 0;
        curToken = findSystemToken("start");
        this.expression = expression;
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