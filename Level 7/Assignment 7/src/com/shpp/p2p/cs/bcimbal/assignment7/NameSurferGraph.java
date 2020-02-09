package com.shpp.p2p.cs.bcimbal.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    private HashMap<Color, NameSurferEntry> entries = new HashMap<>();
    private NameSurferGraphHelper graphBuilder;

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        entries.clear();
        graphBuilder = null;
        update();
    }

    /* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        entries.put(RandomGenerator.getInstance().nextColor(), entry);
        update();
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */

    public void update() {
        removeAll();
        graphBuilder = new NameSurferGraphHelper(entries, getWidth(), getHeight());
        add(graphBuilder.createGraphPanel(GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE));

        /* if some entries are added show graphs*/
        if (entries.size() > 0) {
            add(graphBuilder.createGraphLegend(getWidth(), getHeight()));
            for (GCompound graph : graphBuilder.getEntriesGraphs()) {
                add(graph, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);
            }
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        try {
            for (GCompound graph : graphBuilder.getEntriesGraphs()) {
                GObject obj = getElementAt(e.getX(), e.getY());
                /* hide vertexes and its labels when not focused */
                if (obj == graph) {
                    graph.sendToFront();
                    Color colo = graph.getElement(0).getColor();
                    System.out.println(entries.get(colo).toString());
                    graphBuilder.setObjectVisible(graph, true);
                } else
                    graphBuilder.setObjectVisible(graph, false);
            }
        } catch (Exception ignored) {

        }
    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
