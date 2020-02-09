package com.shpp.p2p.cs.bcimbal.assignment7;

import acm.graphics.*;

import java.awt.*;
import java.util.HashMap;

public class NameSurferGraphHelper extends GCanvas
        implements NameSurferConstants {

    /* width and height to fit all graph components*/
    private double width, height;
    /* hash map of entries*/
    private HashMap<Color, NameSurferEntry> entries;
    /*array of graphs for entries*/
    GCompound[] entriesGraphs;

    /*******************************************************************************************************************
     * Constructor
     *
     * @param entries HashMap of entries
     * @param width   input window width to fit all graph components
     * @param height  input window height to fit all graph components
     */
    public NameSurferGraphHelper(HashMap<Color, NameSurferEntry> entries, double width, double height) {
        this.width = width;
        this.height = height;
        this.entries = entries;
    }

    /*******************************************************************************************************************
     * Method to place Graph Panel at given coordinates
     *
     * @param x double x coordinate of graph panel
     * @param y double y coordinate of graph panel
     * @return GCompound of graph panel
     */
    public GCompound createGraphPanel(double x, double y) {
        GCompound grCanv = graphCanvas(
                width - (x + GRAPH_LEGEND_WIDTH),
                height - (y + GRAPH_TIMELAPSE_HEIGHT));
        add(grCanv, GRAPH_MARGIN_SIZE, GRAPH_MARGIN_SIZE);
        return grCanv;
    }

    /*******************************************************************************************************************
     *  Method to create Graph Panel fitted in given dimensions
     *
     * @param w double width to fit all graph components
     * @param h double height to fit all graph components
     * @return GCompound of graph panel
     */
    private GCompound graphCanvas(double w, double h) {
        GCompound graphPanel = new GCompound();

        GRect graphBackround = new GRect(0, 0, w, h);
        graphBackround.setFillColor(Color.WHITE);
        graphBackround.setFilled(true);
        graphPanel.add(graphBackround);

        /* guid lines*/
        for (int i = 0; i < NDECADES; i++) {
            double x = (i + 1) * getDecadeWidth(graphBackround.getWidth());
            if (i < NDECADES - 1) {
                GLine guid = new GLine(x, 1, x, graphBackround.getHeight() - 1);
                guid.setColor(Color.LIGHT_GRAY);
                graphPanel.add(guid);
            }

            /* decade label*/
            int decade = START_DECADE + i * 10;
            graphPanel.add(new GLabel(
                    "" + decade,
                    x - getDecadeWidth(graphBackround.getWidth()) - 15,
                    graphBackround.getHeight() + 15));
        }

        /* if entries are present*/
        if (entries.size() > 0) {
            entriesGraphs = getEntriesGraphs(graphBackround.getWidth(), graphBackround.getHeight());
        }
        return graphPanel;
    }

    /*******************************************************************************************************************
     * get Decade Width in relation to total width
     *
     * @param totalWidth double total width
     * @return double total width divided on total width
     */
    double getDecadeWidth(double totalWidth) {
        return totalWidth / (NDECADES);
    }

    /*******************************************************************************************************************
     * Method to create Graph Legend at the defined place
     *
     * @param w double given width to place legend at the defined place
     * @param h double given height to place legend at the defined place
     * @return GCompound legend at the defined place
     */
    public GCompound createGraphLegend(double w, double h) {
        /* create legend*/
        GCompound legend = graphLegend();
        add(legend, w - (GRAPH_LEGEND_WIDTH - GRAPH_MARGIN_SIZE), GRAPH_MARGIN_SIZE);
        /* center legend vertically to graph panel*/
        legend.setLocation(
                w - (GRAPH_LEGEND_WIDTH - GRAPH_MARGIN_SIZE),
                GRAPH_MARGIN_SIZE + (h - GRAPH_MARGIN_SIZE - GRAPH_TIMELAPSE_HEIGHT - legend.getHeight()) / 2
        );
        return legend;
    }

    /*******************************************************************************************************************
     * Create graphic visualisation of legend of entries
     *
     * @return GCompound legend of entries
     */
    private GCompound graphLegend() {
        GCompound LegendPanel = new GCompound();
        GRect lPanBackground = new GRect(
                0, 0,
                GRAPH_LEGEND_WIDTH - 2 * GRAPH_MARGIN_SIZE, this.entries.size() * 30
        );
        lPanBackground.setColor(Color.WHITE);
        lPanBackground.setFillColor(Color.WHITE);
        lPanBackground.setFilled(true);
        LegendPanel.add(lPanBackground);

        int cntr = 0;
        for (Color color : this.entries.keySet()) {
            GLine legLine = new GLine(
                    lPanBackground.getX() + 5, 25 + cntr * 30,
                    lPanBackground.getX() + lPanBackground.getWidth() - 7, 25 + cntr * 30
            );
            cntr++;
            legLine.setColor(color);
            LegendPanel.add(legLine);

            LegendPanel.add(createLabel(
                    "" + entries.get(color).getName(),
                    12, true,
                    legLine.getX() + 5, legLine.getY() - 2
            ));
        }
        return LegendPanel;
    }


    /*****************************************************************************************************************
     * Sets visibility parameter of GOval and GLabel of current entry graph
     *
     * @param graph GCompound of entry graph
     * @param visible boolean visibility
     */
    public void setObjectVisible(GCompound graph, boolean visible) {
        for (int i = 0; i < graph.getElementCount(); i++) {
            GObject obj = graph.getElement(i);
            /* hide vertexes and its labels when not focused */
            if (obj instanceof GOval || obj instanceof GLabel)
                graph.getElement(i).setVisible(visible);
        }

    }

    /*******************************************************************************************************************
     * Method to create GLabel with given parameters
     *
     * @param text String content
     * @param size int text size
     * @param visible boolean visibility
     * @param x double x coordinate
     * @param y double y coordinate
     * @return GLabel with given parameters
     */
    GLabel createLabel(String text, int size, boolean visible, double x, double y) {
        GLabel label = new GLabel(text, x, y);
        label.setFont("Default-" + size);
        label.setVisible(visible);
        return label;
    }

    /*******************************************************************************************************************
     * Method to create graphic visualisation for each entry stored in array
     *
     * @param w double width to fit all graph components
     * @param h double height to fit all graph components
     * @return GCompound array graphic visualisation for each entry
     */
    private GCompound[] getEntriesGraphs(double w, double h) {
        GCompound[] eGraph = new GCompound[entries.size()];


        /* draw graph for each entry*/
        int graphCounter = 0;
        for (Color color : entries.keySet()) {
            GCompound c = new GCompound();
            /* define start point of current graph */
            GPoint startPoint = new GPoint(
                    0, h - scaleRank(h, entries.get(color).getRank(0))
            );

            /* create sublines for ranks of current graph*/
            for (int cRank = 1; cRank < NDECADES; cRank++) {
                /* define endpoint of subline*/
                GPoint endPoint = new GPoint(
                        cRank * getDecadeWidth(w),
                        h- scaleRank(h, entries.get(color).getRank(cRank))
                );
                /* create subline*/
                GLine subLine = new GLine(
                        startPoint.getX(), startPoint.getY(),
                        endPoint.getX(), endPoint.getY()
                );
                subLine.setColor(color);
                c.add(subLine);
                /* add vertex for rank of current graph*/
                c.add(createVertex(startPoint.getX(), startPoint.getY(), color));
                c.add(createLabel(
                        "" + entries.get(color).getRank(cRank - 1),
                        12, false,
                        startPoint.getX(), startPoint.getY() - 5
                ));
                startPoint = endPoint;
            }
            /* last rank for current graph */
            c.add(createVertex(startPoint.getX(), startPoint.getY(), color));
            c.add(createLabel(
                    "" + entries.get(color).getRank(NDECADES - 1),
                    12, false,
                    startPoint.getX(), startPoint.getY() - 5

            ));

            /* current graph title */
            c.add(createLabel(
                    "" + entries.get(color).getName(),
                    24, false,
                    GRAPH_MARGIN_SIZE + 20, GRAPH_MARGIN_SIZE + 30
            ));
            eGraph[graphCounter++] = c;
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
     * Method to create vertex at given coordinates
     *
     * @param x double x coordinate
     * @param y double y coordinate
     * @param color Color of vertex
     * @return GOval filled circle centered at x, y
     */
    private GOval createVertex(double x, double y, Color color) {
        GOval p = new GOval(x - 4, y - 4, 8, 8);
        p.setFillColor(color);
        //p.setColor(color);
        p.setFilled(true);
        p.setVisible(false);
        return p;
    }

    /*******************************************************************************************************************
     *
     * @return array of GCompounds of graphs
     */
    GCompound[] getEntriesGraphs() {
        return entriesGraphs;
    }

}
