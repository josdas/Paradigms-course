package ru.ifmo.ctddev.naumov.expression;

/**
 * Created by Stas on 28.03.2017.
 */
public class Subtract extends AbstractBinaryOperator{

    public Subtract(ExpressionAll a, ExpressionAll b) {
        super(a, b);
    }

    protected int operator(int a, int b) {
        return a - b;
    }

    protected double operator(double a, double b) {
        return a - b;
    }
}
