package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class CompressorTable {

    private HashMap<Character, Integer> tableAsMap;
    private byte [] tableAsArray;

    /**
     *
     * @param fileName
     */
    CompressorTable(String fileName) {
        tableAsMap = getCharTable(fileName);
        tableAsArray = convertToArray(tableAsMap);
    }

    /**
     *
     * @return
     */
    public HashMap<Character, Integer> getTableMap() {
        return tableAsMap;
    }

    /**
     *
     * @return
     */
    public byte [] getTableArray() {
        return tableAsArray;
    }

    /**
     *
     * @param fileName
     * @return
     */
    private static HashMap<Character, Integer> getCharTable(String fileName) {
        int totalUnicumChars = 0;
        HashMap<Character, Integer> tmp = new HashMap<>();

        try {
            FileReader reader = new FileReader(fileName);
            char[] tmpBuf = new char[10];
            while (reader.read(tmpBuf) > 0) {
                for (int i = 0; i < tmpBuf.length; i++) {
                    Character ch = tmpBuf[i];
                    if (!tmp.containsKey(ch)) {
                        tmp.put(ch, totalUnicumChars);
                        totalUnicumChars++;
                    }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

//        for (int i = 0; i < inputString.length(); i++) {
//            Character ch = inputString.charAt(i);
//            if (!tmp.containsKey(ch)) {
//                tmp.put(ch, totalUnicumChars);
//                totalUnicumChars++;
//            }
//        }
        return tmp;
    }

    /******************************************************************************************************************/
    private static byte[] convertToArray(HashMap<Character, Integer> charTable) {
        byte[] out = new byte[charTable.size()];
        for (Character ch : charTable.keySet()) {
            out[charTable.get(ch)] = (byte) (ch & 0xff);
        }
        return out;
    }
}
