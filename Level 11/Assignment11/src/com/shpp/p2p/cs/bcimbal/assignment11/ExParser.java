package com.shpp.p2p.cs.bcimbal.assignment11;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExParser {
    private static final String REX_BRACKET = "\\([#0-9a-zA-Z+\\-*^\\/.]+\\)";
    private static final String REX_TRIGONOMETRY = "(sin|cos|tan|atan|log10|log2|sqrt)#\\d+";
    private static final String REX_MUDIVEXP_H = "\\#?[.0-9a-zA-Z#]+\\^#?[.0-9a-zA-Z#]+";
    private static final String REX_MUDIVEXP_L = "\\#?[.0-9a-zA-Z#]+(\\/|\\*)#?[.0-9a-zA-Z#]+";
    private static final String REX_PLUSMINUS = "\\#?[.0-9a-zA-Z#]+(\\+|\\-)#?[.0-9a-zA-Z#]+";

    private HashMap<String, String> parsedExpr = new HashMap<>();
    private int deep = 0;
    private boolean valid = false;

    public ExParser(String expression) {
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        parsedExpr.put("#" + deep++, expression);
        parseBrackets();
        if (valid) {
            parseNormal(REX_TRIGONOMETRY);
            parseNormal(REX_MUDIVEXP_H);
            parseNormal(REX_MUDIVEXP_L);
            parseNormal(REX_PLUSMINUS);
        }
        for (String s : parsedExpr.keySet()) {
            if (parsedExpr.get(s).charAt(0) == '-'
            && parsedExpr.get(s).charAt(1) == '#') {
                parsedExpr.put(s, "0" + parsedExpr.get(s));
            }
            if(parsedExpr.get(s).replaceAll("#\\d+", "").equals("")) {
                parsedExpr.put(s, "0+" + parsedExpr.get(s));
            }
        }

    }

    public HashMap<String, String> getMap() {
        return parsedExpr;
    }

    public boolean isValid() {
        return valid;
    }

    private void parseNormal(String regex) {
        Pattern pattern = Pattern.compile(regex);
        for (int index = 0; index < deep; index++) {
            Matcher matcher = pattern.matcher(parsedExpr.get("#" + index));
            while (matcher.find()) {
                String res = parsedExpr.get("#" + index).substring(matcher.start(), matcher.end());
                if (parsedExpr.get("#" + index).length() > res.length()) {
                    parsedExpr.put("#" + deep, res);

                    for (String s : parsedExpr.keySet()) {
                        String r = parsedExpr.get("#" + deep);
                        parsedExpr.put("#" + index, parsedExpr.get("#" + index).replace(r, "#" + deep));
                    }
                    deep++;
                    matcher = pattern.matcher(parsedExpr.get("#" + index));
                }
            }
        }
    }

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
