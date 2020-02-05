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
        entriesGraphs.clear();
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

        if (entries.size() > 0) {
            for (GCompound graph : entriesGraphs) {
                add(graph, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);
            }
            /* create legend*/
            GCompound legend = graphLegend();
            add(legend, width - (GRAPH_LEGEND_WIDTH - GRAPH_MARGIN_SIZE),
                    GRAPH_MARGIN_SIZE);
            legend.setLocation(
                    width - (GRAPH_LEGEND_WIDTH - GRAPH_MARGIN_SIZE),
                    GRAPH_MARGIN_SIZE + (height - GRAPH_MARGIN_SIZE - GRAPH_TIMELAPSE_HEIGHT - legend.getHeight()) / 2
            );
        }


    }

    ArrayList<GCompound> entriesGraphs;

    private ArrayList<GCompound> getEntriesGraphs(double width, double height) {
        ArrayList<GCompound> eGraph = new ArrayList<>();

        /* draw graph for each entry*/
        for (Color color : entries.keySet()) {
            GCompound c = new GCompound();
            /* define start point of current graph */
            GPoint startPoint = new GPoint(
                    0,
                    height - scaleRank(height, entries.get(color).getRank(0))
            );

            /* create sublines for current entry values*/
            for (int cRank = 1; cRank < NDECADES; cRank++) {
                /* define endpoint of subline*/
                GPoint endPoint = new GPoint(
                        cRank * getDecadeWidth(width),
                        height - scaleRank(height, entries.get(color).getRank(cRank))
                );
                /* create subline*/
                GLine subLine = new GLine(
                        startPoint.getX(), startPoint.getY(),
                        endPoint.getX(), endPoint.getY());
                subLine.setColor(color);
                c.add(subLine);
                /* add vertex for current value of entry*/
                c.add(createVertex(startPoint.getX(), startPoint.getY(), color));
                c.add(createRankLabel(
                        startPoint.getX(), startPoint.getY(),
                        "" + entries.get(color).getRank(cRank - 1))
                );
                if(cRank == NDECADES/ 2)
                    c.add(createRankLabel(
                            startPoint.getX(), GRAPH_MARGIN_SIZE + 30,
                            entries.get(color).getName()));
                startPoint = endPoint;
            }
            c.add(createVertex(startPoint.getX(), startPoint.getY(), color));
            c.add(createRankLabel(
                    startPoint.getX(), startPoint.getY(),
                    "" + entries.get(color).getRank(NDECADES - 1))
            );
            eGraph.add(c);

        }
        return eGraph;
    }

    /*******************************************************************************************************************
     *
     * @param x a
     * @param y a
     * @param rank a
     * @return a
     */
    private GLabel createRankLabel(double x, double y, String rank) {
        GLabel value = new GLabel(rank, x + 2, y - 6);
        value.setVisible(false);
        return value;
    }

    /*******************************************************************************************************************
     *
     * @param x a
     * @param y a
     * @param color a
     * @return a
     */
    private GOval createVertex(double x, double y, Color color) {
        GOval p = new GOval(x - 5, y - 5, 10, 10);
        p.setFillColor(color);
        //p.setColor(color);
        p.setFilled(true);
        p.setVisible(false);
        return p;
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
            if (i < NDECADES - 1) {
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
            entriesGraphs = getEntriesGraphs(gr.getWidth(), gr.getHeight());

        }
        return c;
    }

    /*******************************************************************************************************************
     *
     * @return a
     */
    private GCompound graphLegend() {
        GCompound c = new GCompound();
        GRect bGgr = new GRect(
                0, 0,
                GRAPH_LEGEND_WIDTH - 2 * GRAPH_MARGIN_SIZE, entries.size() * 30);
        bGgr.setColor(Color.WHITE);
        bGgr.setFillColor(Color.WHITE);
        bGgr.setFilled(true);
        c.add(bGgr);

        int cntr = 0;
        for (Color color : entries.keySet()) {
            GLine legLine = new GLine(
                    bGgr.getX() + 5, 25 + cntr * 30,
                    bGgr.getX() + bGgr.getWidth() - 5, 25  + cntr * 30
            );
            cntr++;
            legLine.setColor(color);
            c.add(legLine);

            c.add(createRankLabel(
                    legLine.getX() + 5,
                    legLine.getY() - 1,
                    "" + entries.get(color).getName())
            );
            setGraphVertexVisible(true, c);
        }
        return c;
    }

    double getDecadeWidth(double totalWidth) {
        return totalWidth / (NDECADES);
    }


    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        try {
            for (GCompound graph : entriesGraphs) {
                GObject obj = getElementAt(e.getX(), e.getY());
                if (obj == graph) {
                    Color colo = graph.getElement(0).getColor();
                    System.out.println(entries.get(colo).toString());
                    setGraphVertexVisible(true, graph);
                } else setGraphVertexVisible(false, graph);
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
            GObject obj = graph.getElement(i);
            if (obj instanceof GOval || obj instanceof GLabel)
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
