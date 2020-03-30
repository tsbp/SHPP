package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/*******************************************************************************************************************
 * The class makes archiveing
 */

public class Assignment14Part1 {
    public static void main(String args[]) {

        Packer packer = new Packer ("assets/test.txt", "assets/arch.par");

        Unpacker unp = new Unpacker("assets/arch.par", "assets/arch.uar");
        String unpacked = unpack(unp.getData(), unp.getCharTable());
        System.out.println(unpacked);
    }




        /******************************************************************************************************************/
        private static String unpack ( byte[] input, byte[] charTable){
            int bitsCount = Helper.getBitsCount(charTable.length);
            StringBuilder out = new StringBuilder("");

            LinkedList<Boolean> charSeparator = new LinkedList<>();
            for (byte b : input) {
                for (int n = 0; n < 8; n++) {
                    if ((b & (1 << n)) != 0) {
                        charSeparator.add(true);
                    } else {
                        charSeparator.add(false);
                    }
                    //-------------------------------------------------------
                    if (charSeparator.size() >= bitsCount) {
                        int code = 0;
                        for (int k = 0; k < bitsCount; k++) {
                            if (charSeparator.getFirst()) {
                                code = (char) (code | (1 << k));
                            }
                            charSeparator.removeFirst();
                        }
                        out.append((char) charTable[code]);
                    }
                }
            }
            System.out.println("Before unpacking: " + input.length + ". After: " + out.length());
            return out.toString();
        }

        /******************************************************************************************************************/



        /****************************************************************************************************************/



    }
