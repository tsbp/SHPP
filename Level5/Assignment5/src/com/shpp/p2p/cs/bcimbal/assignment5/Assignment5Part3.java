package com.shpp.p2p.cs.bcimbal.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Assignment5Part3 extends TextProgram  {

    public void run() {

        ArrayList<String> dictionary = readDictionary();

        while (true) {
            String str = readLine("Enter some letters:  ");
            if(str.equals("")) break;
            else findAllWordsMatching(str, dictionary);
        }
    }

    /*******************************************************************************************************************
     *
     * @return arra
     */
    private ArrayList<String> readDictionary() {
        ArrayList<String> tmpList = new ArrayList<>();
        try {
            FileReader fr = new FileReader("assets/en-dictionary.txt");
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
     *
     * @param str oo
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
     *
     * @param word a
     * @param input a
     * @return a
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
