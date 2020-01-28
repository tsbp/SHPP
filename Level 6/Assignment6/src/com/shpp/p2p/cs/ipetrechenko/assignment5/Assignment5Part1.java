package com.shpp.p2p.cs.ipetrechenko.assignment5;

import com.shpp.cs.a.console.TextProgram;


/**
 * Aim: program for counting the number of syllables in a word
 */
public class Assignment5Part1 extends TextProgram {
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         number of syllables in that word.
         */

        while (true) {
            String word = readLine("Enter a single word: ");

            if (word.contains(" ")) {
                println("No spaces");
                continue;
            }

            println(syllablesIn(word));
            println("  Syllable count: " + syllablesIn(word));
        }
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    public int syllablesIn(String word) {
        word = word.toLowerCase();

        //create variable who now vowels letters
        String vowels = "aeiouy";
        // count syllables
        int counter = 0;

        for (int i = 0; i < word.length(); i++) {
            String letter = word.substring(i, i + 1);

            if (letter.equals("e") && i == word.length() - 1) continue;

            if (!vowels.contains(letter)) continue;

            if (i == 0) {
                counter++;
                continue;
            }

            String previous = word.substring(i - 1, i);

            if (!vowels.contains(previous)) {
                counter++;
            }
        }

        if (counter == 0) counter = 1;

        return counter;
    }

}

