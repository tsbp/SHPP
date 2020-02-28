package com.shpp.p2p.cs.bcimbal.assignment10;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.*;

public class Assignment10Part1 {

    private static final String OP_ADD = "+";
    private static final String OP_SUB = "-";
    private static final String OP_MUL = "*";
    private static final String OP_DIV = "/";
    private static final String OP_EXP = "^";

    private static final String OPERATORS = OP_EXP + OP_MUL + OP_DIV + OP_ADD + OP_SUB;
//    private static final String OPERATORS = "*/^+-";



    public static void main(String args[]) {

        String[] tmp = new String[args.length - 1];
        for(int i = 1; i < args.length; i++) {
            tmp[i-1] = args[i];
        }
        Variables varials = new Variables(tmp);


        ArrayList<String> expression = (new Expression(args[0])).getExpression();
        out.println(expression);
        out.println(varials.getVariables());
        out.println("Result: " + calculate(expression, varials));
    }





    private static String calculate(ArrayList<String> splittedExpression, Variables vars) {
        while (splittedExpression.size() > 1) {
            for (int i = 0; i < OPERATORS.length(); i++) {
                String op = OPERATORS.charAt(i) + "";
                int index = getOperatorIndex(op, splittedExpression);
                while (index >= 0) {
                    HashMap<Boolean, Double> a = getValue(splittedExpression.get(index - 1), vars);
                    HashMap<Boolean, Double> b = getValue(splittedExpression.get(index + 1), vars);
                    if (!a.containsKey(false) & !b.containsKey(false)) {
                        switch (op) {
                            case "^":
                                splittedExpression.set(index - 1, (Math.pow(a.get(true), b.get(true))) + "");
                                break;
                            case "*":
                                splittedExpression.set(index - 1, (a.get(true) * b.get(true)) + "");
                                break;
                            case "/":
                                splittedExpression.set(index - 1, (a.get(true) / b.get(true)) + "");
                                break;
                            case "+":
                                splittedExpression.set(index - 1, (a.get(true) + b.get(true)) + "");
                                break;
                            case "-":
                                splittedExpression.set(index - 1, (a.get(true) - b.get(true)) + "");
                                break;
                        }
                        splittedExpression.remove(index);
                        splittedExpression.remove(index);
                        index = getOperatorIndex(op, splittedExpression);
                    }
                    else return "Error in expression";
                }
            }
        }
        return splittedExpression.get(0);
    }


    static HashMap<Boolean, Double> getValue(String toParse, Variables vars){
        HashMap<Boolean, Double> parsed = new HashMap<>();
        try {
           parsed.put(true, Double.parseDouble(toParse));
        }
        catch (Exception e) {
            if(vars.getVariables().containsKey(toParse)) {
                parsed.put(true, vars.getVariables().get(toParse));
            }
            else {
                out.println("Variable " + toParse + " not found");
                parsed.put(false, 0d);
            }
        }
       return parsed;
    }

    static int getOperatorIndex(String operator, ArrayList<String> list) {

        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).equals(operator)) {
                return index;
            }
        }
        return -1;
    }
}
