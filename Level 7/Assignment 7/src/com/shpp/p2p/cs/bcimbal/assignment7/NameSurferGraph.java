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
    private double width, height;

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
        entryGraph.clear();
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

        GCompound grCanv = graphCanvas(
                width - (GRAPH_MARGIN_SIZE + GRAPH_LEGEND_WIDTH),
                height - (GRAPH_MARGIN_SIZE + GRAPH_TIMELAPSE_HEIGHT));
        add(grCanv, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);

        for (GCompound cp : entryGraph) {
            add(cp, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);
        }

    }

    ArrayList<GCompound> entryGraph;

    private ArrayList<GCompound> entriesGraphs(double width, double height) {
        ArrayList<GCompound> eGraph = new ArrayList<>();

        /* draw graph for each entry*/
        for (Color color : entries.keySet()) {
            GCompound c = new GCompound();
            /* define start point of current graph */
            GPoint startPoint = new GPoint(
                    0,
                    height - scaleRank(height, entries.get(color).getRank(0))
            );

            for (int cRank = 1; cRank < NDECADES; cRank++) {
                GPoint endPoint = new GPoint(
                        cRank * getDecadeWidth(width),
                        height - scaleRank(height, entries.get(color).getRank(cRank))
                );
                GLine subLine = new GLine(
                        startPoint.getX(), startPoint.getY(),
                        endPoint.getX(), endPoint.getY());
                subLine.setColor(color);
                c.add(subLine);
                GOval p = new GOval(startPoint.getX() - 5, startPoint.getY() - 5, 10, 10);
                p.setFillColor(color);
                //p.setColor(color);
                p.setFilled(true);
                p.setVisible(false);
                c.add(p);
                startPoint = endPoint;
            }
            eGraph.add(c);

        }
        return eGraph;
    }

    /*******************************************************************************************************************
     *
     * @param rank a
     * @return a
     */
    private int scaleRank(double scaleToRank, int rank) {
        return (int) (rank * (scaleToRank / MAX_RANK));
    }

    /*******************************************************************************************************************
     *
     */

    private static int GRAPH_TIMELAPSE_HEIGHT = 30;
    private static int GRAPH_LEGEND_WIDTH = 100;

    private GCompound graphCanvas(double width, double height) {
        GCompound c = new GCompound();
        GRect gr = new GRect(0, 0, width, height);
        gr.setFillColor(Color.WHITE);
        gr.setFilled(true);
        c.add(gr);
        /* guid lines*/
        for (int i = 0; i < NDECADES; i++) {
            double x = (i + 1) * getDecadeWidth(gr.getWidth());
            if (i < NDECADES - 2) {
                GLine guid = new GLine(x, 1, x, gr.getHeight() - 1);
                guid.setColor(Color.LIGHT_GRAY);
                c.add(guid);
            }
            int decade = START_DECADE + i * 10;
            /* timelapse labels*/
            c.add(new GLabel(
                    "" + decade,
                    x - getDecadeWidth(gr.getWidth()) - 15,
                    gr.getHeight() + 15));
        }

        /* if entries are present*/
        if (entries.size() > 0) {
            entryGraph = entriesGraphs(gr.getWidth(), gr.getHeight());
            /* create legend*/
            c.add(graphLegend());
        }
        return c;
    }

    private GCompound graphLegend() {
        GCompound c = new GCompound();
        GRect bGgr = new GRect(
                0, 0,
                GRAPH_LEGEND_WIDTH - 2 * GRAPH_MARGIN_SIZE, entries.size() * 30);
        bGgr.setColor(Color.WHITE);
        bGgr.setFillColor(Color.WHITE);
        bGgr.setFilled(true);
        bGgr.setLocation(
                width - GRAPH_LEGEND_WIDTH,
                (height - bGgr.getHeight() - GRAPH_TIMELAPSE_HEIGHT - GRAPH_MARGIN_SIZE) / 2);
        c.add(bGgr);
        int cntr = 0;
        for (Color color : entries.keySet()) {
            GLine legLine = new GLine(
                    bGgr.getX() + 5, 20 + bGgr.getY() + cntr * 30,
                    bGgr.getX() + bGgr.getWidth() - 5, 20 + bGgr.getY() + cntr * 30
            );
            cntr++;
            legLine.setColor(color);
            c.add(legLine);
        }
        return c;
    }

    double getDecadeWidth(double totalWidth) {
        return totalWidth / (NDECADES - 1);
    }


    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        try {
            for (GCompound gCompound : entryGraph) {
                GObject obj = getElementAt(e.getX(), e.getY());
                if (obj == gCompound) {
                    Color colo = gCompound.getElement(0).getColor();
                    System.out.println(entries.get(colo).toString());
                    setGraphVertexVisible(true, gCompound);
                } else setGraphVertexVisible(false, gCompound);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * @param visible A
     * @param graph   A
     */
    void setGraphVertexVisible(boolean visible, GCompound graph) {
        for (int i = 0; i < graph.getElementCount(); i++) {
            if (graph.getElement(i) instanceof GOval)
                graph.getElement(i).setVisible(visible);
        }

    }

    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        width = e.getComponent().getWidth();
        height = e.getComponent().getHeight();
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
