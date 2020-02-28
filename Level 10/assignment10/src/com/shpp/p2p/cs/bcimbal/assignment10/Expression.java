package com.shpp.p2p.cs.bcimbal.assignment10;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {
    private boolean valid = false;

    ArrayList<String> splittedExpression;

    public Expression(String expression) {
        splittedExpression = parseExpression(expression);
    }

    public ArrayList<String> getExpression() {
        return splittedExpression;
    }

    private static ArrayList parseExpression (String expression) {
        ArrayList<String> splittedExpression = new ArrayList<>();
        if (expression.charAt(0) == '-') {
            expression = "0" + expression;
        }
        Pattern pattern = Pattern.compile("[a-z0-9\\.\\s]+[/+*^-]");
        Matcher matcher = pattern.matcher(expression);
        int last = 0;
        ArrayList<String> lexems = new ArrayList<>();
        while (matcher.find()) {
            last = matcher.end();
            lexems.add(expression.substring(matcher.start(), last));
        }
        lexems.add(expression.substring(last));



        // --------------------------------------------------------------------
        for (int i = 0; i < lexems.size() - 1; i++) {
            String tmpString = lexems.get(i).trim();
            splittedExpression.add(tmpString.substring(0, tmpString.length() - 1).trim());
            splittedExpression.add(tmpString.substring(tmpString.length() - 1).trim());
        }
        splittedExpression.add(lexems.get(lexems.size() - 1).trim());
        return splittedExpression;
    }
}
