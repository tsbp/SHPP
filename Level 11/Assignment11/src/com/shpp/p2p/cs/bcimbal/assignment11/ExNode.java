package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;

class ExNode {
    String operator;
    ExNode argument1;
    ExNode argument2;

    public ExNode(String root, ExNode arg1, ExNode arg2) {
        this.operator = root;
        this.argument1 = arg1;
        this.argument2 = arg2;
    }
    public Double getResult(HashMap<String, Double> vars) {
        //if(argument1.getResult(vars) == null) return  null;
        try {
            switch (operator) {
                case "+":
                    return argument1.getResult(vars) + argument2.getResult(vars);
                case "-":
                    return argument1.getResult(vars) - argument2.getResult(vars);
                case "*":
                    return argument1.getResult(vars) * argument2.getResult(vars);
                case "/":
                    //TODO division by zero
                    return argument1.getResult(vars) / argument2.getResult(vars);
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
                            System.out.println("Variable " + operator + " not found.");
                            arg = null;
                        }
                    }
                    return arg;
                }
            }
        } catch (Exception e) {
            //System.out.println("Can't make calculations.");
        }
        return  null;
    }
}
