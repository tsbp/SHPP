package com.shpp.p2p.cs.bcimbal.assignment10;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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

    private static HashMap<String, Double> variables;

    public static void main(String args[]) {
        variables = getVariables(args);
        ArrayList<String> splittedExpression = parseExpression(args[0]);
        out.println(calculate(splittedExpression));
    }

    private static HashMap<String, Double> getVariables(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        for(int i = 1; i < args.length; i++) {
            String[] words = args[i].split("=");
            variables.put(words[0].trim(), Double.parseDouble(words[1]));
        }
        return variables;
    }

    private static ArrayList parseExpression (String expression) {
        ArrayList<String> splittedExpression = new ArrayList<>();
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        Pattern pattern = Pattern.compile("[a-z0-9\\.\\s]+[/+*^-]");
        Matcher matcher = pattern.matcher(expression);
        int last = 0;
        ArrayList<String> lexems = new ArrayList<>();
        while (matcher.find()) {
            last = matcher.end();
            lexems.add(expression.substring(matcher.start(), last));
        }
        lexems.add(expression.substring(last));



       // --------------------------------------------------------------------
        for (int i = 0; i < lexems.size() - 1; i++) {
            String tmpString = lexems.get(i).trim();
            splittedExpression.add(tmpString.substring(0, tmpString.length() - 1).trim());
            splittedExpression.add(tmpString.substring(tmpString.length() - 1).trim());
        }
        splittedExpression.add(lexems.get(lexems.size() - 1).trim());
        return splittedExpression;
    }

    private static String calculate(ArrayList<String> splittedExpression) {
        ArrayList<String> result = splittedExpression;
        while (result.size() > 1) {
            for (int i = 0; i < OPERATORS.length(); i++) {
                String op = OPERATORS.charAt(i) + "";
                int index = getOperatorIndex(op, result);
                while (index >= 0) {
                    HashMap<Boolean, Double> a = getValue(result.get(index - 1));
                    HashMap<Boolean, Double> b = getValue(result.get(index + 1));
                    if (!a.containsKey(false) & !b.containsKey(false)) {
                        switch (op) {
                            case "^":
                                result.set(index - 1, (Math.pow(a.get(true), b.get(true))) + "");
                                break;
                            case "*":
                                result.set(index - 1, (a.get(true) * b.get(true)) + "");
                                break;
                            case "/":
                                result.set(index - 1, (a.get(true) / b.get(true)) + "");
                                break;
                            case "+":
                                result.set(index - 1, (a.get(true) + b.get(true)) + "");
                                break;
                            case "-":
                                result.set(index - 1, (a.get(true) - b.get(true)) + "");
                                break;
                        }
                        result.remove(index);
                        result.remove(index);
                        index = getOperatorIndex(op, result);
                    }
                    else return "Error in expression";
                }
            }
        }
        return result.get(0);
    }

    /**
     *
     * @param toParse
     * @return
     */
    static HashMap<Boolean, Double> getValue(String toParse){
        HashMap<Boolean, Double> parsed = new HashMap<>();
        try {
           parsed.put(true, Double.parseDouble(toParse));
        }
        catch (Exception e) {
            if(variables.containsKey(toParse)) {
                parsed.put(true, variables.get(toParse));
            }
            else {
                out.println("Variable " + toParse + " not found");
                parsed.put(false, 0d);
            }
        }
       return parsed;
    }

    static int getOperatorIndex(String operator, ArrayList list) {
        int index = 0;
        for (index = 0; index < list.size(); index++) {
            if (list.get(index).equals(operator)) {
                return index;
            }
        }
        return -1;
    }
}
