package ru.ifmo.ctddev.naumov.parse.token;

import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 31.03.2017.
 */
public abstract class Token {
    protected String name;

    public String getStr() {
        return str;
    }

    protected String str;
    protected TripleExpression operator;

    public Token(String name, String str, TripleExpression operator) {
        this.name = name;
        this.str = str;
        this.operator = operator;
    }

    public Token(String name, String str) {
        this.name = name;
        this.str = str;
    }

    public Token(String name) {
        this.name = name;
    }

    public final boolean equalsName(String temp) {
        return name.equals(temp);
    }

    public final boolean equalsStr(String exp) {
        return str != null && exp.equals(str);
    }
}
