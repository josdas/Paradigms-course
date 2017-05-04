package ru.ifmo.ctddev.naumov.parse.token;

/**
 * Created by Stas on 31.03.2017.
 */
public abstract class Token<T> extends BaseToken<T> {
    public Token(String name, String str) {
        super(name, str);
    }
}
