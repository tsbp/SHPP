package com.shpp.p2p.cs.bcimbal.assignment10;


import java.util.*;

import static java.lang.System.*;

public class Assignment10Part1 {

    /**/
    private static final String OP_ADD = "+";
    private static final String OP_SUB = "-";
    private static final String OP_MUL = "*";
    private static final String OP_DIV = "/";
    private static final String OP_EXP = "^";

    /**/
    private static final String OPERATORS = OP_EXP + OP_MUL + OP_DIV + OP_SUB + OP_ADD;
//    private static final String OPERATORS = "*/^+-";


    public static void main(String[] args) {

        /* print expression*/
        out.println("Expression: " + args[0]);
        /* perform expression parsing */
        Expression expresionObj = new Expression(args[0]);
        if (expresionObj.isExpressionValid()) {
            /* expression parsed successfully*/
            ArrayList<String> expression = expresionObj.getExpression();
            out.println(expression);
            /* perform variables  parsing */
            Variables varials = new Variables(args);
            out.println(varials.getVariables());
            out.println("Result: " + calculate(expression, varials));
        }
        else {
            out.println("Can't make calculation");
        }
    }

    /*******************************************************************************************************************
     * Method to perform calculation of given expression
     * with  input arguments
     *
     * @param splittedExpression expression as ArrayList<String>
     * @param vars object of parsed variables
     * @return String result of calculation
     */
    private static String calculate(ArrayList<String> splittedExpression, Variables vars) {
        if (splittedExpression.size() == 1) {
            return parseValue(splittedExpression.get(0), vars) + "";
        } else
            while (splittedExpression.size() > 1) {
                for (int i = 0; i < OPERATORS.length(); i++) {
                    String op = OPERATORS.charAt(i) + "";
                    int index = getOperatorIndex(op, splittedExpression);
                    while (index >= 0) {
                        Double a = parseValue(splittedExpression.get(index - 1), vars);
                        Double b = parseValue(splittedExpression.get(index + 1), vars);
                        if (a != null && b != null) {
                            switch (op) {
                                case "^":
                                    splittedExpression.set(index - 1, (Math.pow(a, b)) + "");
                                    break;
                                case "*":
                                    splittedExpression.set(index - 1, (a * b) + "");
                                    break;
                                case "/":
                                    if (b == 0) return "Division by zero";
                                    splittedExpression.set(index - 1, (a / b) + "");
                                    break;
                                case "-":
                                    splittedExpression.set(index - 1, (a - b) + "");
                                    break;
                                case "+":
                                    splittedExpression.set(index - 1, (a + b) + "");
                                    break;
                            }
                            splittedExpression.remove(index);
                            splittedExpression.remove(index);

                        } else return "Can't make calculation";
                        index = getOperatorIndex(op, splittedExpression);
                    }
                }
            }
        return splittedExpression.get(0);
    }

    /*******************************************************************************************************************
     * Method to find index of position of inputted operator
     *
     * @param operator String of one char(represent operator)
     * @param list ArrayList<String> to search in
     * @return int searched operator index
     */
    static int getOperatorIndex(String operator, ArrayList<String> list) {

        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).equals(operator)) {
                return index;
            }
        }
        return -1;
    }

    /*******************************************************************************************************************
     * Method parses current string to Double
     * if it is not possible tries fo find current string as argument in map of variables
     * @param value String content to parse
     * @param vars object containing map of variables
     * @return Double result of parsing (null, if not parsed and argunent not declared)
     */
    static Double parseValue(String value, Variables vars) {
        Double result = null;
        try {
            result = Double.parseDouble(value);
        } catch (Exception e) {
            if (vars.getVariables() != null && vars.getVariables().containsKey(value)) {
                result = vars.getVariables().get(value);
            } else {
                out.println("Variable " + value + " not found");
            }
        }
        return result;
    }
}
