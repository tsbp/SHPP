package com.shpp.p2p.cs.ipetrechenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Aim: the License Plate Game
 */
public class Assignment5Part3 extends TextProgram {

    public void run() {
        giveWords();
    }

    /**
     * Asks for three letters and calculates the words
     * which can be made with them and displays them on the screen
     */
    private void giveWords() {
        while (true) {
            String letters = readLine("Enter three letter :").toLowerCase();
            if (letters.equals("")) {
                break;
            }
            silentWords(letters);
        }
    }

    /**
     * method that checks every word in construction
     * and returns true if there is a word
     *
     * @param line    - contain a line with the word
     * @param letters - letters entered by user
     * @return - returns true or false if found the word
     */
    private boolean findWords(String line, String letters) {
        int in = -1;
        for (int i = 0; i < letters.length(); i++) {

            String let = letters.substring(i, i + 1);
            int lw = line.indexOf(let, in + 1);
            if (lw == -1) {
                return false;
            }
            in = lw;
        }
        return true;
    }

    /**
     * checks each line with accounting letters that the user entered
     * if he finds the word displays it
     *
     * @param letters - letters that the user entered
     */
    private void silentWords(String letters) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("assets/en-dictionary.txt"));
            while (true) {
                String line = br.readLine();
                if (line == null) break;

                if (findWords(line, letters)) {
                    println(line);
                }
            }
            br.close();
        } catch (Exception e) {
            println("Warning");
        }
    }
}
