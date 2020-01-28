package com.shpp.p2p.cs.ipetrechenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Aim: CSV parsing
 */
public class Assignment5Part4 extends TextProgram {

    // check if the file found, displays content
    public void run() {
        ArrayList<String> s = extractColumn("IgorPetrechenko/assets/country.csv", 0);

        if (s == null) {
            return;
        }

        for (String val : s) {
            println(val);
        }
    }

    /**
     * the method that transfers the columns from the file
     * through their index in the list, while displaying on the screen
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> res = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            while (true) {
                String line = br.readLine();
                if (line == null) break;

                ArrayList<String> fields = fieldsIn(line);
                String val = fields.get(columnIndex);

                /* checks the first and last character in a string if (") skips
                 and cuts the value without him*/
                if (val.charAt(0) == '"') {
                    val = val.substring(1, val.length() - 1);
                }

                res.add(val);
            }
            br.close();
        } catch (Exception e) {
            println("Warning");
            return null;
        }
        return res;
    }

    /**
     * Кeads a line of the file, write its value up to the character (") or value (,) and fill the list
     *
     * @param line - line of the file
     * @return - ArrayList<String> lines
     */
    private ArrayList<String> fieldsIn(String line) {

        ArrayList<String> lines = new ArrayList<>();
        //begin index
        int start = 0;

        while (true) {
            char sym = line.charAt(start);

            //check values without symbol (") and (,) get next index
            if (sym == '"') {
                int end = line.indexOf('"', start + 1);
                String part = line.substring(start, end + 1);
                lines.add(part);
                start = end + 2;
            } else {
                int end = line.indexOf(',', start);
                if (end == -1) break;
                String part = line.substring(start, end);
                lines.add(part);
                start = end + 1;
            }

            if (start > line.length()) break;
        }

        if (start < line.length()) {
            String part = line.substring(start);
            lines.add(part);
        }
        return lines;
    }
}

