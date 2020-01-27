package com.shpp.p2p.cs.bcimbal.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.program.*;
import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private static String BUTTON1_ACTION = "Graph";
    private static String BUTTON2_ACTION = "Clear";
    NameSurferDataBase dataBase;
    NameSurferGraph graph;
    JTextField textField;

	/* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {

        this.add(new JLabel("Name"), "North");

        textField = new JTextField("anna", 30);
        this.add(textField, "North");

        this.add(new JButton(BUTTON1_ACTION), "North");
        this.add(new JButton(BUTTON2_ACTION), "North");
        this.addActionListeners();

        dataBase = new NameSurferDataBase("assets/" + NameSurferConstants.NAMES_DATA_FILE);
        graph = new NameSurferGraph();
        this.add(graph);
    }

	/* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
       if(e.getActionCommand().equals(BUTTON1_ACTION)) {
           String request = textField.getText();
           /* if something input*/
           if (request.length() > 0) {

               /* convert to sentence case*/
               String a = request.substring(0, 1);
               String b = request.substring(1);
               request = a.toUpperCase() + b.toLowerCase();

               NameSurferEntry entry = dataBase.findEntry(request);
               if (entry != null) {
                   graph.addEntry(entry);
                   System.out.println(BUTTON1_ACTION + ": " + entry.toString());
               } else System.out.println("Name not found");
           }
       }
       else if(e.getActionCommand().equals(BUTTON2_ACTION)) {
           System.out.println(BUTTON2_ACTION);
       }
    }
}
