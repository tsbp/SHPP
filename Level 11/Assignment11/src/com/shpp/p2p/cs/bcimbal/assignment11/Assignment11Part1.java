package com.shpp.p2p.cs.bcimbal.assignment11;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Scanner;

public class Assignment11Part1 {
    public static final String expr = "-2*(sin(3.141592/2)+cos(b))^2";//"-atan(-1.25)^(-4)+2*cos(3+2*(tan(1.3+1.7)*sin((0.1*10^100)-7)+log2(0.5^3)))";//""tan(a+78)+((a-a)+(2+3)*(4+5-sin(45*cos(a))))/7";

    public static void main(String[] args) {
        Variables vars = new Variables(args);
        ExTree exTree = new ExTree(expr);

        System.out.println(exTree.getResult(vars.getVariables()));
        Scanner sc = new Scanner(System.in);
        while(true){
            String in = sc.nextLine();
            //System.out.println("Input: " + in);
            in = "," + in;
            String[] a = in.split(",");
            vars = new Variables(a);
            HashMap<String, Double> v = vars.getVariables();
            System.out.println(exTree.getResult(v));
        }
    }
}