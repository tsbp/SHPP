package com.shpp.p2p.cs.bcimbal.assignment2;
//======================================================================================================================
// program solves quadratic equation ax^2 + bx + c = 0
//======================================================================================================================
import acm.graphics.GLabel;

import com.shpp.cs.a.console.TextProgram;
//import acm.program.*;

public class Assignment2Part1 extends TextProgram  {
    public void run() {

        //
        println("Lets solve quadratic equation ax^2 + bx + c = 0 with given a, b, c");

        double a = readDouble("Enter a: ");
        double b = readDouble("Enter b: ");
        double c = readDouble("Enter c: ");

        double d = discr(a, b, c);

        print(a + "x^2 + "+ b + "x + " + c + "= 0 has ");
        if(d < 0){
            println("no real roots");
        }
        else if(d == 0) {
            println("one root: " + root(a, b, c, Math.sqrt(d)));
        }
        else {
            print("two roots: ");//+ total + ".");
            print(             root(a, b, c,           Math.sqrt(d)));
            println(" and " +  root(a, b, c, (-1) * Math.sqrt(d)));
        }

    }

    //==================================================================================================================
    // program solves quadratic equation ax^2 + bx + c = 0
    // =================================================================================================================
    double discr(double a, double b, double c)   {
        return (b * b - 4 * a* c);
    }

    //==================================================================================================================
    // program solves quadratic equation ax^2 + bx + c = 0
    // =================================================================================================================
    double root (double a, double b, double c, double d)
    {
        return ((-1) * b + d) / (2 * a);
    }



//    public static void main(String[] args) {
//        new Assignment2Part1().start(args);
//
//    }

}

