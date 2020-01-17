package com.shpp.p2p.cs.bcimbal.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Assignment5Part4 extends TextProgram {

    private static final String FILENAME = "my-csv.csv";

    public void run() {

        ArrayList<String> columnData;

        while (true) {
            int index = readInt("Enter colmn number:  ");
            columnData = extractColumn(FILENAME, index);
            if (columnData == null)
                println("Column with index " + index + " does not exist");
            else
                println(columnData);
        }
    }

    /*******************************************************************************************************************
     *
     * @param filename o
     * @param columnIndex o
     * @return o
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> row = readFromFile(filename);

        ArrayList<String>[] temporary = new ArrayList[row.size()];
        for (int i = 0; i < row.size(); i++) temporary[i] = new ArrayList<>();

        for (int k = 0; k < row.size(); k++) {
            int startPosition = 0;
            boolean quote = false;
            for (int i = startPosition; i < row.get(k).length(); i++) {
                char ch = row.get(k).charAt(i);
                if (ch == '"') quote = !quote;


                boolean last = false;
                if (i + 1 >= row.get(k).length()) {
                    ch = ',';
                    last = true;
                }

                if (ch == ',' && !quote) {
                    String a;
                    if (last) a = row.get(k).substring(startPosition);
                    else a = row.get(k).substring(startPosition, i);
                    temporary[k].add(a);
                    startPosition = i + 1;
                }
            }
        }

        if (columnIndex < 1 || temporary[0].size() < columnIndex)
            return null;
        else {
            ArrayList<String> out = new ArrayList<>();
            for (ArrayList<String> strings : temporary) {
                out.add(strings.get(columnIndex - 1));
            }
            return out;
        }

//        return out;
    }

    /*******************************************************************************************************************
     *
     * @param filename f
     * @return o
     */
    private ArrayList<String> readFromFile(String filename) {
        ArrayList<String> row = new ArrayList<>();
        try {
            FileReader fr = new FileReader("assets/" + filename);
            BufferedReader br = new BufferedReader(fr);
            while (true) {
                String str = br.readLine();
                if (str == null) break;
                row.add(str);
            }
        } catch (Exception e) {
            println(":-(");
        }
        return row;
    }
}
