package com.shpp.p2p.cs.bcimbal.original.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Assignment5Part4 extends TextProgram {

    private static final String FILENAME = "assets/my-csv.csv";


    public void run() {

        ArrayList<String> columnData;

        while (true) {
            int index = readInt("Enter column number:  ");
            columnData = extractColumn(FILENAME, index);
            
            if (columnData != null) {
                if (columnData.size() > 0) println(columnData);
            }
            else break;
        }
    }

    /*******************************************************************************************************************
     * Extract column
     *
     * @param filename String filename
     * @param columnIndex int column index will be extracted
     * @return ArrayList<String> representation of requested column
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> fileContent = readFromFile(filename);

        /* create ArrayList to return*/
        ArrayList<String> out = new ArrayList<>();
        if (fileContent != null) {

            try {
                for (String field : fileContent) {
                    String str = fieldsIn(field).get(columnIndex).trim();
                    /* remove external quotes if present */
                    if (str.length() > 1 && str.charAt(0) == '"') str = str.substring(1, str.length() - 1);

                    /* make double quotes inside as single */
                    StringBuilder sb = new StringBuilder(str);
                    int dQuoteIndex = sb.indexOf("\"\"");
                    while (dQuoteIndex != -1) { // -1 - double quote not found
                        sb = sb.replace(dQuoteIndex, dQuoteIndex + 2, "\"");
                        dQuoteIndex = sb.indexOf("\"\"");
                    }
                    out.add(sb.toString());
                }
            } catch (IndexOutOfBoundsException e) {
                println("Column with index " + columnIndex + " does not exist");
            }
        }
        return out;

    }

    /*******************************************************************************************************************
     * parse input string to arraylist
     *
     * @param line input string
     * @return ArrayList of children
     */
    private ArrayList<String> fieldsIn(String line) {

        ArrayList<String> tmp = new ArrayList<>();
        int startPosition = 0; //index of string next parse iteration will be started with
        boolean isQuoted = false; // current child is quoted

        /* pasrse current string and add it to ArrayList*/
        for (int currentPosition = startPosition; currentPosition < line.length(); currentPosition++) {
            char ch = line.charAt(currentPosition);


            /* if field is quoted */
            if (ch == '"') isQuoted = !isQuoted;

            boolean last = false;
            /*check for <eos>*/
            if (currentPosition + 1 >= line.length()) {
                ch = ',';
                last = true;
                if (line.charAt(currentPosition - 1) == ',') {
                    tmp.add("");
                    startPosition++;
                }
            }
            /* double quote inside field*/
            else if (isQuoted &&
                    ch == '"' &&
                    line.charAt(currentPosition + 1) == '"' &&
                    line.charAt(currentPosition + 2) != ',') {
                currentPosition++;
            }

            /* field has been found*/
            if (ch == ',' && !isQuoted) {
                String a;
                if (last) a = line.substring(startPosition);
                else a = line.substring(startPosition, currentPosition);
                tmp.add(a);
                startPosition = currentPosition + 1;
            }
        }

        return tmp;
    }

    /*******************************************************************************************************************
     * Reads file rows as array list.
     *
     * @param filename String filename
     * @return ArrayList<String> representation of file rows
     */
    private ArrayList<String> readFromFile(String filename) {
        ArrayList<String> row = new ArrayList<>();
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);

            while (true) {
                String str = br.readLine();

                if (str == null) break;

                /* read complete string checking for CR LF*/
                while (!isStringComplete(str)) {
                    str += "\r\n" + br.readLine(); // adding CR LF manually
                }
                row.add(str);
            }
            br.close();
        } catch (IOException e) {
            println("File not found");
            return null;
        }
        return row;
    }

    /*******************************************************************************************************************
     * check if string complete by counting double quotes
     *
     * @param str input string
     * @return true if string complete
     */
    private boolean isStringComplete(String str) {
        int qConter = 0;

        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '"') qConter++;

        return qConter % 2 == 0;
    }
}
