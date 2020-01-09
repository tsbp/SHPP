package com.shpp.p2p.cs.bcimbal.assignment4;

import acm.graphics.*;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.util.RandomGenerator;
import com.shpp.cs.a.graphics.WindowProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Rules extends GCompound {

    public  Rules() {
        try {
            /* Open the file for reading. */
            FileReader fr = new FileReader("assets/rules.txt");
            BufferedReader br = new BufferedReader(fr);
            double offset = 0;
            /* Process the file by adding one magnet per line. */
            while (true) {

                String line = br.readLine();
                if (line == null) break;

                addRule(line, offset);
                offset += 30;
            }

            br.close();
        } catch (IOException e) {
//            println(":-(");
        }

    }

    private void addRule(String text, double offset) {

        GLabel label = new GLabel(text);
        label.setColor(Color.BLACK);
        label.setFont("Arial-" + 20);
        label.setLocation(0, offset);
        add(label);
    }
}
