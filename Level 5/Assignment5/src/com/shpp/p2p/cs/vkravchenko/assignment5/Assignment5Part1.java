// File: Assignment5Part1.java
package com.shpp.p2p.cs.vkravchenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

/*
 * Assignment5Part1 -- Counting the number of syllables.
 */
public class Assignment5Part1 extends TextProgram {
    /* character code a */
    private static final int CODE_FIRST = 97;
    /* character code z */
    private static final int CODE_LAST = 122;

    /**
     * A method for counting syllables in English.
     * Repeatedly prompt the user for a word and print out the estimated
     * Result: number of syllables in that word.
     */
    public void run() {
        try {
            while (true) {
                String word;
                do {
                    word = inputWord();
                } while (checkWord(word));
                println("Syllable count: " + syllablesInWord(word));
            }
        } catch (Exception e) {
            println("Infinite sadness");
        }
    }

    /**
     * A method that counts the number of syllables
     * necessary counts the number of vowels
     *
     * @param word - the word being tested
     * @return number of syllables in that word
     */
    private int syllablesInWord(String word) {
        word = word.toLowerCase();
        int countSyllables = 0;
        boolean statusSymbol = true;
        for (int i = 0; i < word.length() - 1; i++) {
            if (checkVowel(word, i)) {
                if (statusSymbol) {
                    countSyllables++;
                }
                statusSymbol = false;
            } else {
                statusSymbol = true;
            }
        }
        countSyllables = checkLastLetter(word, statusSymbol, countSyllables);
        return countSyllables;
    }

    /**
     * A method that takes into account the last letter.
     *
     * @param word           -- A string containing a single word.
     * @param statusSymbol   -- boolean values if consonant letter -> true, else -> false
     * @param countSyllables -- number of syllables in that word( last letter not included )
     * @return An estimate of the number of syllables in that word.
     */
    private int checkLastLetter(String word, boolean statusSymbol, int countSyllables) {
        if (checkVowel(word, word.length() - 1)) {
            if (countSyllables > 0) {
                if (statusSymbol && !(word.charAt(word.length() - 1) == 'e')) {
                    countSyllables++;
                }
            } else {
                if (statusSymbol) {
                    countSyllables++;
                }
            }
        }
        return countSyllables;
    }

    /**
     * A method that checks which is vocal or consonant
     *
     * @param word -- the word being tested
     * @param i    --  number of letters
     * @return boolean values (true or false)
     */
    private boolean checkVowel(String word, int i) {
        return word.charAt(i) == 'a' || word.charAt(i) == 'e' || word.charAt(i) == 'i'
                || word.charAt(i) == 'o' || word.charAt(i) == 'u' || word.charAt(i) == 'y';
    }

    /**
     * A method is used to enter a single word.
     * lowercase and uppercase characters can be used.
     *
     * @return word typed by the user
     */
    private String inputWord() {
        println("Enter a single word:");
        return readLine();
    }

    /**
     * Searches for non-English characters
     *
     * @param word - the word being tested
     * @return boolean values (true or false)
     */
    private boolean checkWord(String word) {
        word = word.toLowerCase();
        boolean resultCheck = false;
        for (int i = 0; i < word.length(); i++) {
            if ((int) (word.charAt(i)) < CODE_FIRST || (int) (word.charAt(i)) > CODE_LAST) {
                resultCheck = true;
                break;
            }
        }
        if (resultCheck) {
            println("Please, repeat!");
        }
        return resultCheck;
    }

}
