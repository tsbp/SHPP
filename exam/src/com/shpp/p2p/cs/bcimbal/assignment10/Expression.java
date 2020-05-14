package com.shpp.p2p.cs.bcimbal.assignment10;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Class Expression makes parsing input string to array list of operators an operands*/
public class Expression {

    /* array list of parsed expression*/
    private ArrayList<String> splittedExpression;
    /* true if expression parsed without errors*/
    private static boolean valid = true;

    /* Constructor */
    public Expression(String expression) {
        splittedExpression = parseExpression(expression);
        valid = splittedExpression != null;
    }

    /*******************************************************************************************************************
     * Get parsed expression
     * @return ArrayList<String> of parsed expression
     */
    public ArrayList<String> getExpression() {
        return splittedExpression;
    }

    /*******************************************************************************************************************
     * Method to parse input string to array list of operators an operands
     * @param expression String to be parsed
     * @return ArrayList<String> of parsed expression
     */
    private static ArrayList<String> parseExpression(String expression) {

        /*delete spaces*/
        expression = expression.replaceAll(" ", "");

        /* split in lexemes like operator-operand (12+, aa*, a^)*/
        if (expression.charAt(0) == '-') { // if first argument is negative
            expression = "0" + expression;
        }
        Pattern pattern = Pattern.compile("[a-z0-9.\\s]+[/+*^-]");
        Matcher matcher = pattern.matcher(expression);
        int last = 0;
        ArrayList<String> lexems = new ArrayList<>();
        while (matcher.find()) {
            last = matcher.end();
            lexems.add(expression.substring(matcher.start(), last));
        }
        /* add last operand*/
        String a = expression.substring(last);
        lexems.add(a.replaceAll("[^a-z0-9.\\s]", ""));


        /* check expression correctness*/
        StringBuilder tmp = new StringBuilder();
        for (String s : lexems) {
            tmp.append(s);
            if (!expression.contains(tmp)) {
                System.out.println("Error in expression before " + s);
                return null;
            }
        }

        /* generate splitted expression by operators and operands*/
        ArrayList<String> temporary = new ArrayList<>();
        for (int i = 0; i < lexems.size() - 1; i++) {
            String tmpString = lexems.get(i).trim();
            temporary.add(tmpString.substring(0, tmpString.length() - 1).trim());
            temporary.add(tmpString.substring(tmpString.length() - 1).trim());
        }
        temporary.add(lexems.get(lexems.size() - 1).trim());
        return temporary;

    }

    /*******************************************************************************************************************
     * Get parsed expression status
     * @return boolean parsed expression status (true if expression parse without errors)
     */
    public boolean isExpressionValid() {
        return valid;
    }
}
