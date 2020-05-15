package com.shpp.p2p.cs.bcimbal.assignment11;

//import java.util.HashMap;
import com.shpp.p2p.cs.bcimbal.assignment17.HHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***********************************************************************************************************************
 * Class creates a tree of ExNode objects
 */
class ExTree {
    ExNode root;

    /*******************************************************************************************************************
     * Constructor
     * @param expression String input expression
     */
    public ExTree(String expression) {
        ExParser pExpression = new ExParser(expression);
        if (pExpression.isValid()) {
            // System.out.println("map: " + pExpression.getMap());
            root = createNode(("#0"), pExpression.getMap());
        }
    }

    /*******************************************************************************************************************
     * Creating node
     * @param root String  node info
     * @param map HashMap of rules to build tree
     * @return ExNode node
     */
    private ExNode createNode(String root, HHashMap<String, String> map) {
        HHashMap<String, String> nInfo;
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

    /*******************************************************************************************************************
     * Getting node info from string
     * @param input String text representation of node info
     * @return HashMap node info
     */
    private HHashMap<String, String> getNodeInfo(String input) {
        HHashMap<String, String> tmp = new HHashMap<>();
        /* mark negative value*/
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

    /*******************************************************************************************************************
     *
     * @return ExNode tree of expression
     */
    public ExNode getTree() {
        return root;
    }

    /*******************************************************************************************************************
     *
     * @param vars HashMap of parsed variables for expression
     * @return Double result (null if can't calculate)
     */
    public Double getResult(HHashMap<String, Double> vars) {
        return root.getResult(vars);
    }
}
