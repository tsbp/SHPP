package com.shpp.p2p.cs.bcimbal.assignment10;

import java.util.HashMap;



public class Variables {

    private static HashMap<String, Double> variables;

    public Variables (String [] args) {
        variables = parseVariables(args);
    }

    public  HashMap<String, Double> getVariables() {
        return variables;
    }

    private static HashMap<String, Double> parseVariables(String[] args) {
        HashMap<String, Double> variables = new HashMap<>();
        for(int i = 0; i < args.length; i++) {
            String[] words = args[i].split("=");
            variables.put(words[0].trim(), Double.parseDouble(words[1]));
        }
        return variables;
    }


}
