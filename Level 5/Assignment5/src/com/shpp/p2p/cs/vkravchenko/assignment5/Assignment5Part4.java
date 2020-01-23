// File: Assignment5Part4.java
package com.shpp.p2p.cs.vkravchenko.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/*
 * Assignment5Part4 -- CSV parsing
 */
public class Assignment5Part4 extends TextProgram {
    /**
     * Runs the program, the method sets two values.
     * Displays an array -- values ​​of a specific column of the CSV file
     */
    public void run() {
        int columnIndex = 1; // 1 -- column number of the csv file
        String filename = "assets/food-origins.csv";
        ArrayList<String> arrayList = extractColumn(filename, columnIndex);
        printArray(arrayList);
    }

    /**
     * A method creates an ArrayList array using a column number
     *
     * @param filename    -- filename
     * @param columnIndex -- column number
     * @return An ArrayList dynamic array
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> arrayExtraColumn = new ArrayList<>();
        ArrayList<String> arrayProcess;
        ArrayList<String> arrayResult = new ArrayList<>();
        try {
            arrayExtraColumn = readFile(filename);
        } catch (IOException e) {
            println("Infinite sadness!");
        }
        for (String line : arrayExtraColumn) {
            arrayProcess = fieldsIn(line);
            try {
                arrayResult.add(arrayProcess.get(columnIndex));
            } catch (Exception e) {
                println("Missing error!");
            }
        }
        return arrayResult;
    }

    /**
     * A method that reads a CSV file and creates an array
     *
     * @param line -- CSV file line
     * @return An ArrayList dynamic array
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> arrayField = new ArrayList<>();
        String[] arrStr = line.split(",");
        for (int i = 0; i < arrStr.length; i++) {
            if (arrStr[i].indexOf('"') == 0 && arrStr[i + 1].lastIndexOf('"') == (arrStr[i + 1].length() - 1)) {
                String firstLine = arrStr[i].substring(1).concat(",");
                arrayField.add(firstLine.concat(arrStr[i + 1].substring(0, arrStr[i + 1].length() - 1)));
                i++;
            } else {
                arrayField.add(arrStr[i]);
            }
        }
        return arrayField;
    }

    /**
     * A method outputs an array to the console
     *
     * @param arrayList -- column of the rows
     */
    private void printArray(ArrayList<String> arrayList) {
        println(Arrays.toString(arrayString(arrayList)));
    }

    /**
     * A method returns a string array
     *
     * @param arrayList -- column of the rows
     * @return string array
     */
    private String[] arrayString(ArrayList<String> arrayList) {
        String[] arrayString = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            arrayString[i] = '"' + arrayList.get(i) + '"';
        }
        return arrayString;
    }

    /**
     * A method that reads a CSV file and creates an array.
     *
     * @param filename -- the name of the file that opens
     * @return An ArrayList dynamic array (values are lines of the CSV file)
     * @throws IOException -- caught bug
     */
    private ArrayList<String> readFile(String filename) throws IOException {
        ArrayList<String> arrayLine = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            arrayLine.add(line);
        }
        br.close();
        return arrayLine;
    }
}
