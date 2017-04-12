package ru.ifmo.ctddev.naumov.parse.oparator;


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
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
