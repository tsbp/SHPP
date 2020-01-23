// File: Assignment5Part3.java
package com.shpp.p2p.cs.vkravchenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* Assignment5Part3 -- Playing on the road */
public class Assignment5Part3 extends TextProgram {
    /*  indexOf, there is no such character */
    private static final int STOP = -1;
    /* ascii, 'a' has the int value 97 */
    private static final int NUMBER_FIRST = 97;
    /* ascii, 'z' has the int value 122 */
    private static final int NUMBER_LAST = 122;
    /* the word consists of three letters */
    private static final int THREE_LETTER = 3;

    /**
     * Runs the program, which will ask the user for a line of three letters
     * and then output words that can be made up of those letters.
     */
    public void run() {
        printWords();
    }

    /**
     * A method will adjust the order of execution of other methods
     * and prints found words.
     */
    private void printWords() {
        String filename = "assets/en-dictionary.txt";
        String lineSymbols = readWord();
        String[] arraySymbols = getSymbols(lineSymbols);
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            arrayList = readFile(filename);
        } catch (IOException e) {
            println("Infinite sadness");
        }
        ArrayList arrayWords = processWords(arrayList, arraySymbols[0], arraySymbols[1], arraySymbols[2]);
        for (Object word : arrayWords) {
            println(word);
        }
    }

    /**
     * The method looks for the right words and adds it to the array
     *
     * @param arrayList    -- an ArrayList (verifiable words)
     * @param firstSymbol  -- the first letter is a three letter line
     * @param secondSymbol -- the second letter is a three letter line
     * @param thirdSymbol  -- the third letter is a three letter line
     * @return An ArrayList dynamic array
     */
    private ArrayList processWords(ArrayList<String> arrayList, String firstSymbol, String secondSymbol, String thirdSymbol) {
        ArrayList<String> arrayWords = new ArrayList<>();
        for (String word : arrayList) {
            String wordCheck = word.toLowerCase();
            int numberFirst = wordCheck.indexOf(firstSymbol);
            if (numberFirst != STOP) {
                int numberSecond = wordCheck.indexOf(secondSymbol, numberFirst + 1);
                if (numberSecond != STOP) {
                    if (wordCheck.indexOf(thirdSymbol, numberSecond + 1) != STOP) {
                        arrayWords.add(word);
                    }
                }
            }
        }
        return arrayWords;
    }

    /**
     * A method breaks a given string around matches of the given regular expression.
     *
     * @param lineOfMethod -- a line of three letters
     * @return an array of substrings
     */
    private String[] getSymbols(String lineOfMethod) {
        return lineOfMethod.toLowerCase().split("");
    }

    /**
     * The method is used to enter a string of three letters
     *
     * @return a line of three letters
     */
    private String readWord() {
        String lineOfMethod;
        int j = 0;
        println("Enter a three-letter word:");
        do {
            if (j++ > 0) println("Repeat, please!");
            int i = 0;
            do {
                if (i++ > 0) println("You pressed only enter!");
                lineOfMethod = readLine();
            } while (lineOfMethod.equals(""));
        } while (checkWord(lineOfMethod));
        return lineOfMethod;
    }

    /**
     * Searches for non-English characters
     * for words less than three letters long -- true
     * for words more than three letters -- true
     *
     * @param lineMethod --  the word being tested
     * @return true or false
     */
    private boolean checkWord(String lineMethod) {
        boolean statusMethod = false;
        lineMethod = lineMethod.toLowerCase();
        for (int i = 0; i < lineMethod.length(); i++) {
            if ((int) lineMethod.charAt(i) < NUMBER_FIRST || (int) lineMethod.charAt(i) > NUMBER_LAST) {
                statusMethod = true;
                break;
            }
        }
        if (lineMethod.length() != THREE_LETTER) {
            statusMethod = true;
        }
        return statusMethod;
    }

    /**
     * A method that reads a txt file and creates an array.
     *
     * @param filename -- the name of the file that opens
     * @return an ArrayList (words read from a file)
     * @throws IOException --caught bug
     */
    private ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(filename));
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            arrayList.add(line);
        }
        br.close(); // throw
        return arrayList;
    }
}
