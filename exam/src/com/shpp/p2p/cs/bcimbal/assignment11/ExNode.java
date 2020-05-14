package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;

/*******************************************************************************************************************
 * Class for node of simple math expression "argument1 operator argument2" (a+b etc.)
 */
class ExNode {
    String operator;
    ExNode argument1;
    ExNode argument2;

    /*******************************************************************************************************************
     * Constructor to create node
     * @param root String node functional
     * @param arg1 ExNode as argument one
     * @param arg2 ExNode as argument two
     */
    public ExNode(String root, ExNode arg1, ExNode arg2) {
        this.operator = root;
        this.argument1 = arg1;
        this.argument2 = arg2;
    }

    /*******************************************************************************************************************
     * Method to get result of mathematical operations
     * @param vars map of variables
     * @return Double result of mathematical operations (null if can't make)
     */
    public Double getResult(HashMap<String, Double> vars) throws ArithmeticException {
        //try {
        switch (operator) {
            case "+":
                return argument1.getResult(vars) + argument2.getResult(vars);
            case "-":
                return argument1.getResult(vars) - argument2.getResult(vars);
            case "*":
                return argument1.getResult(vars) * argument2.getResult(vars);
            case "/":
                Double a2Result = argument2.getResult(vars);
                if (a2Result == 0) {
                    throw new ArithmeticException();
                }
                return argument1.getResult(vars) / a2Result;
            case "^":
                return Math.pow(argument1.getResult(vars), argument2.getResult(vars));
            case "cos":
                return Math.cos(argument1.getResult(vars));
            case "sin":
                return Math.sin(argument1.getResult(vars));
            case "tan":
                return Math.tan(argument1.getResult(vars));
            case "atan":
                return Math.atan(argument1.getResult(vars));
            case "log10":
                return Math.log10(argument1.getResult(vars));
            case "log2":
                return (Math.log10(argument1.getResult(vars)) / Math.log10(2));
            case "sqrt":
                return Math.sqrt(argument1.getResult(vars));
            default: {
                Double arg;
                try {
                    arg = Double.parseDouble(operator);
                } catch (Exception e) {
                    if (vars.containsKey(operator)) {
                        arg = vars.get(operator);
                        System.out.println(operator + " = " + arg);
                    } else {
                        System.out.println("Unknown type of " + operator + ".");
                        arg = null;
                    }
                }
                return arg;
            }
        }
//        } catch (Exception e) {
//            //System.out.println("Can't make calculations.");
//        }
//        return  null;
    }
}
