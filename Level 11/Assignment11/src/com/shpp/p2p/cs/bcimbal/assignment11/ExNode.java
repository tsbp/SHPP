package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExNode {
    String operator;
    ExNode argument1;
    ExNode argument2;


    public ExNode(String root, HashMap<String, String> map) {
        HashMap<String, String> tmp;
        if (root.charAt(0) == '#') tmp = getNodeInfo(map.get(root));
        else tmp = getNodeInfo(root);
        this.operator = tmp.get("operator");
        if (tmp.get("arg1") == null) argument1 = null;
        else argument1 = new ExNode(tmp.get("arg1"), map);
        if (tmp.get("arg2") == null) argument2 = null;
        else argument2 = new ExNode(tmp.get("arg2"), map);
    }

    private HashMap<String, String> getNodeInfo(String input) {
        HashMap<String, String> tmp = new HashMap<>();
        if(input.charAt(0) == '-')
            input = input.replaceFirst("-", "!");

        Pattern pattern = Pattern.compile("(sin|cos|tan|atan|log10|log2|sqrt|/|\\^|\\+|\\*|-)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            String op = input.substring(matcher.start(), matcher.end());
            tmp.put("operator", op);
            switch (op) {
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                    String[] a = input.split("\\" + op);
                    tmp.put("arg1", a[0]);
                    tmp.put("arg2", a[1]);
                    break;
                case "sin":
                case "cos":
                case "tan":
                case "atan":
                case "log10":
                case "log2":
                case "sqrt":
                    tmp.put("arg1", input.replace(op, ""));
                    tmp.put("arg2", null);
                    break;
            }
        } else {
            tmp.put("operator", input.replaceFirst("!","-"));
            tmp.put("arg1", null);
            tmp.put("arg2", null);
        }
        return tmp;
    }

    public Double getResult(HashMap<String, Double> vars) {
        //if(argument1.getResult(vars) != null && argument2.getResult(vars) != null) {

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
                            System.out.println("Variable " + operator + "not found.");
                            arg = null;
                        }
                    }
                    return arg;
                }
            }
//        }
//        return null;
    }
}
