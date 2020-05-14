package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.Scanner;

/**
 * The program calculates formulas with variables given as arguments
 * Supported:
 * - multiplication; division, subtraction, summation and exponentiation
 * - sin, cos, tan, atan, log10, log2, sqrt.
 * Limitations:
 * - not discovered.
 */
public class Assignment11Part1 {

    /*******************************************************************************************************************
     * Main
     * @param args String[] program arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Expression: " + args[0]);
            Variables vars = new Variables(args);
            ExTree exTree = new ExTree(args[0].replaceAll(" ", ""));
            if (exTree.getTree() != null) {
                makeCalculations(exTree, vars);
            }
        } else {
            System.out.println("Expression not found");
        }
    }

    /*******************************************************************************************************************
     * Cycle making calculations with user variables
     * @param tree ExTree tree of expression
     * @param vars Variables object that stores user variables
     */
    private static void makeCalculations(ExTree tree, Variables vars) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                HashMap<String, Double> v = vars.getVariables();
                Double result = tree.getResult(v);
                if (result != null) {
                    System.out.println("Result: " + result);
                } else {
                    System.out.println("Can't make calculations.");
                }
            } catch (ArithmeticException aEx) {
                System.out.println("Division by zero.");
            }
            System.out.println("To calculate expression with your variables use coma as separator (a=23, abc=-23.5, a23c=58)");
            vars = new Variables(("," + sc.nextLine()).split(","));
        }
    }
}