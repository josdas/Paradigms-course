package ru.ifmo.ctddev.naumov.expression;


/**
 * Created by Stas on 28.03.2017.
 */
public class Variable implements ExpressionAll {
    String name;

    public Variable(String s) {
        name = s;
    }

    public double evaluate(double x) {
        return x;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        if(name.equals("x")) {
            return x;
        } else if(name.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
