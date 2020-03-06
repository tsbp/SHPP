package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;

class ExTree {
    ExNode root;
    public ExTree(String expression) {
        ExParser pExpression = new ExParser(expression);
        HashMap<String, String> parsedMap;
        if(pExpression.isValid()) {
            parsedMap = pExpression.getMap();
            System.out.println("map: " + parsedMap);
            root = new ExNode("#0", parsedMap);
        }

    }
    public ExNode getTree() {
        return root;
    }
    public double getResult(HashMap<String, Double> vars) {
        return root.getResult(vars);
    }
}
