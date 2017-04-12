package ru.ifmo.ctddev.naumov.parse.operator;


import ru.ifmo.ctddev.naumov.parse.TripleExpression;

/**
 * Created by Stas on 28.03.2017.
 */
public class Variable implements TripleExpression {
    String name;

    public Variable(String s) {
        name = s;
    }

    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            default:
                return z;
        }
    }
}
