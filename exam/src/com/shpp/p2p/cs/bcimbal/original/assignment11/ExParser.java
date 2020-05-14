package com.shpp.p2p.cs.bcimbal.original.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  ExParser builds a sequence of simple operations
 *  for input expression
 */
public class ExParser {
    /* regular expression for parsing brackets */
    private static final String REX_BRACKET = "\\([#0-9a-zA-Z+\\-*^\\/.]+\\)";
    /* regular expression for parsing trigonometry functions */
    private static final String REX_TRIGONOMETRY = "(sin|cos|tan|atan|log10|log2|sqrt)#\\d+";
    /* regular expression for parsing power */
    private static final String REX_MUDIVEXP_H = "\\#?[.0-9a-zA-Z#]+\\^#?[.0-9a-zA-Z#]+";
    /* regular expression for parsing division and multiplying */
    private static final String REX_MUDIVEXP_L = "\\#?[.0-9a-zA-Z#]+(\\/|\\*)#?[.0-9a-zA-Z#]+";
    /* regular expression for parsing adding and subtracting */
    private static final String REX_PLUSMINUS = "\\#?[.0-9a-zA-Z#]+(\\+|\\-)#?[.0-9a-zA-Z#]+";

    /* A sequence of simple operations of parsed expression*/
    private HashMap<String, String> parsedExpr = new HashMap<>();
    /* a deep of parsing */
    private int deep = 0;
    /* validity of expression */
    private boolean valid = true;

    /*******************************************************************************************************************
     * Object to build a sequence of simple operations
     * from input expression
     * @param expression String input expression
     */
    public ExParser(String expression) {
        /* preform expression first sign minus*/
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        /* mark as first step*/
        parsedExpr.put("#" + deep++, expression);
        parseBrackets();
        if (valid) {
            parseNormal(REX_TRIGONOMETRY);
            parseNormal(REX_MUDIVEXP_H);
            parseNormal(REX_MUDIVEXP_L);
            parseNormal(REX_PLUSMINUS);

            for (String s : parsedExpr.keySet()) {
                /* first char is " " than make 0-*/
                if (parsedExpr.get(s).charAt(0) == '-'
                        && parsedExpr.get(s).charAt(1) == '#') {
                    parsedExpr.put(s, "0" + parsedExpr.get(s));
                }
                /* quote in quote proceeding*/
                if (parsedExpr.get(s).replaceAll("#\\d+", "").equals("")) {
                    parsedExpr.put(s, "0+" + parsedExpr.get(s));
                }
            }
            checkForMathCorrectness();
        }

     }

    /*******************************************************************************************************************
     * Method to check mathematical correction
     * of parsed expression map
     */
    private void checkForMathCorrectness() {
        for(String s: parsedExpr.values()) {
            String a = s;
            a = a.replaceAll("[.0-9a-zA-Z#]+", "");
            if(a.length() > 1) {
                System.out.println("Error in expression: " + a);
                valid = false;
            }
        }
    }

    /*******************************************************************************************************************     *
     * @return HashMap sequence of simple operations
     */
    public HashMap<String, String> getMap() {
        return parsedExpr;
    }

    /*******************************************************************************************************************     *
     * @return boolean validity of parsed map
     */
    public boolean isValid() {
        return valid;
    }

    /*******************************************************************************************************************
     * Makes parsing of map with regular expression
     * @param regex String regular expression to be parsed with
     */
    private void parseNormal(String regex) {
        Pattern pattern = Pattern.compile(regex);
        for (int index = 0; index < deep; index++) {
            Matcher matcher = pattern.matcher(parsedExpr.get("#" + index));
            while (matcher.find()) {
                String res = parsedExpr.get("#" + index).substring(matcher.start(), matcher.end());
                /* if matches regular expression */
                if (parsedExpr.get("#" + index).length() > res.length()) {
                    parsedExpr.put("#" + deep, res);
                    /* make replacement */
                    for (String s : parsedExpr.keySet()) {
                        String r = parsedExpr.get("#" + deep);
                        parsedExpr.put("#" + index, parsedExpr.get("#" + index).replace(r, "#" + deep));
                    }
                    deep++;
                    /* next match */
                    matcher = pattern.matcher(parsedExpr.get("#" + index));
                }
            }
        }
    }

    /*******************************************************************************************************************
     * Makes parsing of map for brackets
     */
    private void parseBrackets() {
        while (parsedExpr.get("#0").contains("(") || parsedExpr.get("#0").contains(")")) {
            Pattern pattern = Pattern.compile(REX_BRACKET);
            Matcher matcher = pattern.matcher(parsedExpr.get("#0"));
            valid = false;
            if (matcher.find()) {
                String res = parsedExpr.get("#0").substring(matcher.start(), matcher.end());
                if (parsedExpr.get("#0").length() > res.length()) {
                    //System.out.println(res);
                    parsedExpr.put("#" + deep, res);
                    /* make replacement */
                    for (String s : parsedExpr.keySet()) {
                        String r = parsedExpr.get("#" + deep);
                        parsedExpr.put("#0", parsedExpr.get("#0").replace(r, "#" + deep));
                    }
                    /* delete outer brackets */
                    if (parsedExpr.get("#" + deep).charAt(0) == '(') {
                        String tmp = parsedExpr.get("#" + deep);
                        parsedExpr.put("#" + deep, tmp.substring(1, tmp.length() - 1));
                    }
                    deep++;
                    valid = true;
                }
            } else {
                System.out.print("Error in expression. Expected ");
                if (parsedExpr.get("#0").contains("(")) System.out.println("\")\".");
                else if (parsedExpr.get("#0").contains(")")) System.out.println("\"(\".");
                return;
            }
        }
    }
}
