package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Assignment11Part1 {
    public static final String expr = "1+2*cos(3+2*((1.3+1.7)*sin((0.1*100)-7)+(4-3)))";//""tan(a+78)+((a-a)+(2+3)*(4+5-sin(45*cos(a))))/7";

    public static void main(String[] args) {
        BinaryTree a = new BinaryTree(expr);
    }
}

class Node {
    String operator;
    Node argument1;
    Node argument2;

    public Node(String root, HashMap<String, String> map) {
        HashMap<String, String> tmp;
        if (root.charAt(0) == '#') tmp = getNodeInfo(map.get(root));
        else tmp = getNodeInfo(root);
        this.operator = tmp.get("operator");
        if (tmp.get("arg1") == null) argument1 = null;
        else argument1 = new Node(tmp.get("arg1"), map);
        if (tmp.get("arg2") == null) argument2 = null;
        else argument2 = new Node(tmp.get("arg2"), map);
    }

    private HashMap<String, String> getNodeInfo(String input) {
        HashMap<String, String> tmp = new HashMap<>();
        Pattern pattern = Pattern.compile("(sin|cos|\\+|\\*|-)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String op = input.substring(matcher.start(), matcher.end());
            switch (op) {
                case "+":
                case "-":
                case "*":
                    String[] a = input.split("\\" + op);
                    tmp.put("operator", op);
                    tmp.put("arg1", a[0]);
                    tmp.put("arg2", a[1]);
                    break;
            }
        } else {
            tmp.put("operator", input);
            tmp.put("arg1", null);
            tmp.put("arg2", null);
        }

        return tmp;
    }

    public double getResult() {
        switch (operator) {
            case "+":
                return argument1.getResult() + argument2.getResult();
            case "-":
                return argument1.getResult() - argument2.getResult();
            case "*":
                return argument1.getResult() * argument2.getResult();
            default:
                return Double.parseDouble(operator);
        }
    }
}

class BinaryTree {
    Node root;

    public BinaryTree(String expression) {
        ExParser pExpression = new ExParser(expression);
        HashMap<String, String> parsedMap = pExpression.getMap();
        System.out.println("map: " + parsedMap);
        root = new Node("#0", parsedMap);
        double result = root.getResult();
        System.out.println("Result: " + result);
    }
}
