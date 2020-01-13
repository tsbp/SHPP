package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

public class Rules extends GCompound {

    private static int FONT_SIZE = 20;
    private static int LABEL_OFFSET = FONT_SIZE + 5;
    private static String FONT_SET = "Arial";


    public  Rules() {
        try {
            /* Open the file for reading. */
            FileReader fr = new FileReader("assets/rules.txt");
            BufferedReader br = new BufferedReader(fr);
            double offset = 0;
            /* Process the file by adding one string per line. */
            while (true) {

                String line = br.readLine();
                if (line == null) break;

                showText(line, offset);
                offset += LABEL_OFFSET;
            }

            br.close();
        } catch (IOException e) {
           // println(":-(");
        }

    }

    /**
     *
     * @param text  String label text
     * @param offset double text left offset
     */
    private void showText(String text, double offset) {

        GLabel label = new GLabel(text);
        label.setColor(Color.WHITE);
        label.setFont(FONT_SET + "-" + FONT_SIZE);
        label.setLocation(0, offset);
        add(label);
    }
}
