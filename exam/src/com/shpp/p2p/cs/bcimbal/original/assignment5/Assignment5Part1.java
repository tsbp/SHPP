package com.shpp.p2p.cs.bcimbal.original.assignment5;

import com.shpp.cs.a.console.TextProgram;

public class Assignment5Part1 extends TextProgram {

    private static final String WOVELS = "aeiouy";

    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            println("  Syllable count: " + syllablesIn(word));
        }

    }

    /*******************************************************************************************************************
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesIn(String word) {
        //word = word.toLowerCase();
        int cntr = 0;
        for (int i = 0; i < word.length(); i++) {
            /* if current char is vowel add 1 to counter*/
            if (isCharVowel(word.charAt(i)))
                cntr++;
            /* if current char is not first and vowel, char before is vowel reduce counter*/
            if (i > 0 && isCharVowel(word.charAt(i)) && isCharVowel(word.charAt(i - 1)))
                cntr--;
        }
        /* if vowels more then one and last syllable consist of consonant and 'e' reduce counter*/
        if (cntr > 1 && !isCharVowel(word.charAt(word.length() - 2)) && word.charAt(word.length() - 1) == 'e')
            cntr--;

        return cntr;
    }

    /*******************************************************************************************************************
     * Check if char is vowel
     * @param ch input char
     * @return true i f char is vowel
     */
    private boolean isCharVowel(char ch) {
        for (int i = 0; i < WOVELS.length(); i++) {
            if (((Character) WOVELS.charAt(i)).toString().equalsIgnoreCase(((Character) ch).toString()))
                return true;
        }
        return false;
    }

}
