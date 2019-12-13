package com.shpp.p2p.cs.bcimbal.assignment2;
/*
 * program solves quadratic equation ax^2 + bx + c = 0
 * with your values of a, b and c
 */

import com.shpp.cs.a.console.TextProgram;

public class Assignment2Part1 extends TextProgram {

    /*******************************************************************************************************************
     * main method
     */
    public void run() {
        println("Lets solve quadratic equation ax^2 + bx + c = 0 with given a, b, c");

        /* read values of a, b and c */
        double a = readDouble("Enter a: ");
        double b = readDouble("Enter b: ");
        double c = readDouble("Enter c: ");

        /* calculate discriminant */
        double d = discr(a, b, c);

        /*calculate roots and print result */
        print(a + "x^2 + " + b + "x + " + c + "= 0 has ");
        /* if discriminant les 0 we have no real roots */
        if (d < 0) {
            println("no real roots");
        }
        /* if discriminant equal 0 we have one real root */
        else if (d == 0) {
            println("one root: " + root(a, b, c, Math.sqrt(d)));
        }
        /* if discriminant greater than 0 we have two real roots */
        else {
            print("two roots: ");
            print(root(a, b, c, Math.sqrt(d)));    // print value of first root
            println(" and " + root(a, b, c, (-1) * Math.sqrt(d)));   // print value of second root
        }

    }

    /*******************************************************************************************************************
     * Method to find discriminant of quadratic equation ax^2 + bx + c = 0
     *
     * @param aA The a constant of quadratic equation ax^2 + bx + c = 0
     * @param aB The b constant of quadratic equation ax^2 + bx + c = 0
     * @param aC The c constant of quadratic equation ax^2 + bx + c = 0
     * @return double value of discriminant of quadratic equation ax^2 + bx + c = 0
     */
    double discr(double aA, double aB, double aC) {
        return (aB * aB - 4 * aA * aC);
    }

    /*******************************************************************************************************************
     * Method to find one of roots of quadratic equation ax^2 + bx + c = 0
     *
     * @param aA The a constant of quadratic equation ax^2 + bx + c = 0
     * @param aB The b constant of quadratic equation ax^2 + bx + c = 0
     * @param aC The c constant of quadratic equation ax^2 + bx + c = 0
     * @param aD The square root of discriminant of quadratic equation ax^2 + bx + c = 0
     * @return double value of one of root of quadratic equation ax^2 + bx + c = 0
     */
    double root(double aA, double aB, double aC, double aD) {
        return ((-1) * aB + aD) / (2 * aA);
    }
}

