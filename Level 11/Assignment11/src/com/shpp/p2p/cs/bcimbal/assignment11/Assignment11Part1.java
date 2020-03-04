package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Assignment11Part1 {

    private static final String REX_BRACKET_G = "\\(.+?\\)+";
    public static final String expr ="1+2";// "tan(a+78)+((a-a)+(2+3)*(4+5-sin(45*cos(a))))/7";

    public static void main(String[] args){

        BinaryTree a = new BinaryTree(expr);


    }
}
class Node {
    String operator;
    Node argument1;
    Node argument2;
    public Node (String operator){
        this.operator = operator;
        argument1 = null;
        argument2 = null;
    }


}
class BinaryTree {
    private static final String REX_BRACKET = "\\(.+?\\)+";
    private static final String REX_TRIGONOMETRY = "(cos|sin|tan)#\\d+";
    private static final String REX_MUDIVEXP = "#?\\d+(\\^|\\/|\\*)#?\\d+";
    private static final String REX_PLUSMINUS = "#?\\d+(\\+|\\-)#?\\d+";
    Node root;

    public BinaryTree(String expression){
        parsedExpr.put("#" + deep++, expression);
        parseBrackets(0);
        //parseTrigonometry(0);

        parseNormal(REX_TRIGONOMETRY);
        System.out.println("123");
        parseNormal( REX_MUDIVEXP);
        System.out.println("123");
        parseNormal(REX_PLUSMINUS);
        System.out.println("123");
    }

    private void parseNormal(String regex) {
        for(int index =0; index < deep; index++) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(parsedExpr.get("#" + index));
            while (matcher.find()) {
                String res = parsedExpr.get("#" + index).substring(matcher.start(), matcher.end());
                if (parsedExpr.get("#" + index).length() > res.length()) {
                    System.out.println(res);
                    parsedExpr.put("#" + deep, res);//getSubExpression(res, 0, res.length()));
                    for (String s : parsedExpr.keySet()) {
                        String r = parsedExpr.get("#" + deep);
                        parsedExpr.put("#" + index, parsedExpr.get("#" + index).replace(r, "#" + deep));
                    }
                    deep++;
                }
            }
        }
    }

    HashMap<String, String> parsedExpr = new HashMap<>();
    int deep = 0;

    private void parseBrackets(int index) {
        Pattern pattern = Pattern.compile(REX_BRACKET);
        Matcher matcher = pattern.matcher(parsedExpr.get("#" + index));
        while (matcher.find()) {
            String res = parsedExpr.get("#" + index).substring(matcher.start(), matcher.end());
            System.out.println(res);
            parsedExpr.put("#" + deep, getSubExpression(res, 1, res.length() - 1));
            deep++;
        }

        for(String s: parsedExpr.keySet()) {
            String r = "(" + parsedExpr.get(s) + ")";
            parsedExpr.put("#" + index, parsedExpr.get("#" + index).replace(r, s));
        }

        if( parsedExpr.get("#" + index).contains("(")) parseBrackets( index);
        else {
            for(int i = 0; i < parsedExpr.size(); i++) {
                while(parsedExpr.get("#" + i).contains("("))
                    parseBrackets(i);
            }
        }
    }

    private String getSubExpression(String str, int begin, int end){
        //int begin = 1;
        //int end = str.length() - 1;
        int lb = 0;
        int rb = 0;
        while (str.charAt(begin) == '(') begin++;
        for(int i = begin; i < end; i++){
            if(str.charAt(i) == '(') lb++;
            if(str.charAt(i) == ')') rb++;
            if(lb==rb && (lb != 0 && rb!=0)) {
                end = i + 1;
                break;
            }
        }
        return str.substring(begin, end);
    }
    private Node addRecursive(Node current, String value) {
        if (current == null) {
            return new Node(value);
        }

        current.operator =  value.charAt(1) + "";
        current.argument1 = addRecursive(current.argument1,value.charAt(0) + "");
        current.argument2 = addRecursive(current.argument2,value.charAt(2) + "");

        return current;
    }
    public void add(String value) {
        root = addRecursive(root, value);
    }
}
