package ru.ifmo.ctddev.naumov.test;

import ru.ifmo.ctddev.naumov.parse.*;
public class MyTest {
    public static void main(String[] args) throws Exception {
        try {
            TripleExpression exp = new ExpressionParser().parse("x*y)+(z-1   )/10");
        } catch (Exception e) {
            System.out.print("Gotcha");
        }
    }
}
