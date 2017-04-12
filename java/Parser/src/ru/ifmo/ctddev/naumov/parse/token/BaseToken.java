package ru.ifmo.ctddev.naumov.parse.token;

/**
 * Created by Stas on 31.03.2017.
 */
public class BaseToken {
    protected String name;
    protected String str;

    public BaseToken(String name, String str) {
        this.name = name;
        this.str = str;
    }

    public BaseToken(String name) {
        this.name = name;
    }

    public boolean equalsName(String temp) {
        return name.equals(temp);
    }

    public boolean equalsStr(String exp) {
        return str != null && exp.equals(str);
    }

    public String getStr() {
        return str;
    }
}
