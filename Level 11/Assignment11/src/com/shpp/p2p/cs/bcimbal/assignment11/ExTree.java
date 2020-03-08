package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExTree {
    ExNode root;

    public ExTree(String expression) {
        ExParser pExpression = new ExParser(expression);
        if (pExpression.isValid()) {
            System.out.println("map: " + pExpression.getMap());
            root = createNode(("#0"), pExpression.getMap());
        }
    }

    private ExNode createNode(String root, HashMap<String, String> map) {
        HashMap<String, String> nInfo;
        ExNode node = null;
        if (root != null) {
            if (root.charAt(0) == '#') nInfo = getNodeInfo(map.get(root));
            else nInfo = getNodeInfo(root);
            node = new ExNode(
                    nInfo.get("operator"),
                    createNode(nInfo.get("arg1"), map),
                    createNode(nInfo.get("arg2"), map));
        }
        return node;
    }

    private HashMap<String, String> getNodeInfo(String input) {
        HashMap<String, String> tmp = new HashMap<>();
        if (input.charAt(0) == '-')
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
            tmp.put("operator", input.replaceFirst("!", "-"));
            tmp.put("arg1", null);
            tmp.put("arg2", null);
        }
        return tmp;
    }

    public ExNode getTree() {
        return root;
    }

    public Double getResult(HashMap<String, Double> vars) {
        return root.getResult(vars);
    }
}
