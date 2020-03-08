package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.Scanner;

public class Assignment11Part1 {
    public static final String expr = "-2-(-cos(-sin((-3.99+tan(a))))+1)";//"-atan(-1.25)^(-4)+2*cos(3+2*(tan(1.3+1.7)*sin((0.1*10^100)-7)+log2(0.5^3)))";//""tan(a+78)+((a-a)+(2+3)*(4+5-sin(45*cos(a))))/7";

    public static void main(String[] args) {
        Variables vars = new Variables(args);
        ExTree exTree = new ExTree(expr.replaceAll(" ", ""));
        if(exTree.getTree() != null) {
            makeCalculations(exTree, vars);
        }
    }

    private static void makeCalculations(ExTree tree, Variables vars) {
        Scanner sc = new Scanner(System.in);
        while(true){
            HashMap<String, Double> v = vars.getVariables();
            Double result = tree.getResult(v);
            if(result != null) {
                System.out.println("Result: " + result);
            }
            else {
                System.out.println("Can't make calculations.");
            }

            String in = sc.nextLine();
            //System.out.println("Input: " + in);
            in = "," + in;
            String[] a = in.split(",");
            vars = new Variables(a);
        }
    }
}