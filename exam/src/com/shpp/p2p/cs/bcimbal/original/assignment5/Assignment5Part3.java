package com.shpp.p2p.cs.bcimbal.original.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Assignment5Part3 extends TextProgram  {

    private static final String DICTIONARY ="assets/en-dictionary.txt";

    public void run() {

        ArrayList<String> dictionary = readDictionary(DICTIONARY);

        while (true) {
            String str = readLine("Enter some letters:  ");
            if(str.equals("")) break;
            else findAllWordsMatching(str, dictionary);
        }
    }

    /*******************************************************************************************************************
     * Reads file with given filename
     *
     * @param filename filename string
     * @return ArrayList<String> representation file content
     */

    private ArrayList<String> readDictionary(String filename) {
        ArrayList<String> tmpList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                String str = br.readLine();
                if (str == null) break;
                else tmpList.add(str);
            }
        } catch (Exception e) {
            println(":-(");
        }
        return tmpList;
    }

    /*******************************************************************************************************************
     * Print words from dictionary matching given string
     *
     * @param str input string
     * @param dict  ArrayList<String> dictionary
     */
    private void findAllWordsMatching(String str, ArrayList<String> dict) {

        int cntr = 0;
        for (int i = 0; i < dict.size(); i++)
            if (isWordMatching(dict.get(i), str)) {
                println(i + ": " + dict.get(i));
                cntr++;
            }

        println("Words totally matched - " + cntr);
    }

    /*******************************************************************************************************************
     * Check if input string matches given word
     *
     * @param word word to check
     * @param input input string
     * @return true input matches word
     */
    private boolean isWordMatching(String word, String input) {
        input = input.toLowerCase();
        int matches = 0;
        int beginPosition = 0;
        for (int j = 0; j < input.length(); j++) {
            for (int a = beginPosition; a < word.length(); a++)
                if (word.charAt(a) == input.charAt(j)) {
                    matches++;
                    beginPosition = a + 1;
                    break;
                }
        }
        return (matches >= (input.length()));
    }
}
