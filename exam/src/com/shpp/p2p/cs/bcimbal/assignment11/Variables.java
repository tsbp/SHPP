package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;

/* Class Variables makes parsing input array of string to map of variable values*/

public class Variables {

    /*  Map of variables values*/
    private static HashMap<String, Double> variables = new HashMap<>();

    /* Constructor */
    public Variables(String[] args) {
        parseVariables(args);
    }

    /*******************************************************************************************************************
     *
     * @return parsed map of variables values
     */
    public HashMap<String, Double> getVariables() {
        return variables;
    }

    /*******************************************************************************************************************
     * Method to parse variables
     * @param args array of strings with expected variables values
     * @return map of parsed variables
     */
    private static void parseVariables(String[] args) {

        /* begin parse from index 1 (0 - expression)*/
        for (int i = 1; i < args.length; i++) {
            try {
                String[] words = args[i].split("=");
                variables.put(words[0].trim(), Double.parseDouble(words[1]));
            } catch (Exception e) {
                /* can't split or parse double*/
                System.out.println("Can't parse argument " + args[i]);
            }
        }
    }


}
