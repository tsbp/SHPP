package com.shpp.p2p.cs.bcimbal.assignment11;

public class Assignment11Part1 {
    public static final String expr = "-atan(-1.25)^(-4)+2*cos(3+2*(tan(1.3+1.7)*sin((0.1*10^100)-7)+log2(0.5^3)))";//""tan(a+78)+((a-a)+(2+3)*(4+5-sin(45*cos(a))))/7";

    public static void main(String[] args) {
        Variables vars = new Variables(args);
        ExTree exTree = new ExTree(expr);

        System.out.println(exTree.getResult(vars.getVariables()));
    }
}