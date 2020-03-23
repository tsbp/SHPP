package com.shpp.p2p.cs.bcimbal.assignment13;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*******************************************************************************************************************
 * The class counts the number of silhouettes in the picture
 * by the method "breadth-first search, BFS" without recursion
 */

public class Assignment13Part1 extends JFrame {

    /* Array of discovered silhouettes */
    private static ArrayList<HashMap<Point, Integer>> silhouettes = new ArrayList<>();

    /* The threshold of luminance between silhouettes and background*/
    private static final double LUMINANCE_THRESHOLD = 20;
    private static final double MIN_SILHOUETTE_SIZE = 100;

    /* Array to perform searching around pixel */
    private static final int[][] searchMatrix = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1},
            {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    /*******************************************************************************************************************
     * Makes counting of silhouettes in input image (file name as argument)
     * if argument not set tries to open "test.jpg"
     *
     * @param args String program arguments
     */
    public static void main(String[] args) {

        String filepath = "";
        BufferedImage img = null;

        if (args.length > 0) {
            filepath = "assets\\" + args[0];
            img = readImageFromFile(filepath);
        }

        if (img == null) {
            /* if args are invalid or empty, try to read test.jpg */
            filepath = "assets\\" + "test.jpg";
            img = readImageFromFile(filepath);
        }

        if (img != null) {
            System.out.println("Silhouettes count (" + filepath + "): " + getSilhouettesCount(img));
        }

        ArrayList<HashMap<Point, Integer>> contours = new ArrayList<>();
        countourize(silhouettes, contours, 4);

        showDiscovered(img.getWidth(), img.getHeight(), contours);
    }

    /*******************************************************************************************************************
     * Get outlines of input silhouettes
     *
     * @param silhouettes ArrayList of  silhouettes
     * @param contours ArrayList of  contours
     * @param contourWidth int number of iterations of contourizing
     */
    private static void countourize(ArrayList<HashMap<Point, Integer>> silhouettes,
                                    ArrayList<HashMap<Point, Integer>> contours,
                                    int contourWidth) {
        for (int n = 0; n < contourWidth; n++) {
            boolean first = false;
            if (contours.size() <= 0) {
                first = true;
            }

            for (int i = 0; i < silhouettes.size(); i++) {
                if (first) {
                    contours.add(getContour(silhouettes.get(i), new HashMap<>()));
                } else {
                    getContour(silhouettes.get(i), contours.get(i));
                }
            }
            substrate(contours, silhouettes);
        }
    }

    /*******************************************************************************************************************
     * Substrate contours from silhouettes
     *
     * @param contours ArrayList of  contours
     * @param silhouettes ArrayList of  silhouettes
     */
    private static void substrate(ArrayList<HashMap<Point, Integer>> contours,
                                  ArrayList<HashMap<Point, Integer>> silhouettes) {
        for (int i = 0; i < contours.size(); i++) {
            for (Point p : contours.get(i).keySet()) {
                silhouettes.get(i).remove(p);
            }
        }
    }

    /*******************************************************************************************************************
     * Method to gets contour from input silhouette
     *
     * @param silh HashMap of  silhouette
     * @param contr HashMap of contour
     * @return HashMap of contour
     */
    private static HashMap<Point, Integer> getContour(HashMap<Point, Integer> silh,
                                                      HashMap<Point, Integer> contr) {
        for (Point pix : silh.keySet()) {
            for (int[] matrix : searchMatrix) {
                if (!silh.containsKey(new Point(pix.x + matrix[0], pix.y + matrix[1]))) {
                    if (!contr.containsKey(pix)) {
                        contr.put(pix, silh.get(pix));
                    }
                }
            }
        }
        return contr;
    }

    /*******************************************************************************************************************
     * Method to count silhouettes
     *
     * @param img BufferedImage input image object
     * @return int silhouettes number
     */
    private static int getSilhouettesCount(BufferedImage img) {
//        /* define as background luminance at 0,0 */
        int bgColor = img.getRGB(0, 0);
        /* proceed each pixel*/
        for (int x = 1; x < img.getWidth() - 1; x++) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                double pixLumi = getLuminance(img.getRGB(x, y));
                if (!isLuminancesEqual(pixLumi, getLuminance(bgColor)) && !belongsTo(x, y, silhouettes)) {
                    /* found a pixel with luminance not equal background */
                        HashMap<Point, Integer> silhouette = discoverSilhouette(img, bgColor, x, y);
                        if (silhouette.size() >= MIN_SILHOUETTE_SIZE) {
                            silhouettes.add(silhouette);
                    }
                }
//                else
//                    bgColor = img.getRGB(x, y);
            }
        }
        return silhouettes.size();
    }

    /*******************************************************************************************************************
     * Method to discover silhouette
     *
     * @param image BufferedImage input image object
     * @param bgColor int background luminance
     * @param xC int x coordinate of current pixel
     * @param yC int y coordinate of current pixel
     * @return HashMap of discovered silhouette
     */
    private static HashMap<Point, Integer> discoverSilhouette(BufferedImage image, int bgColor, int xC, int yC) {
        HashMap<Point, Integer> silhouetteMap = new HashMap<>();
        silhouetteMap.put(new Point(xC, yC), image.getRGB(xC, yC));

        /* breadth-first search, BFS */
        LinkedList<Point> queue = new LinkedList<>();
        queue.add(new Point(xC, yC));
        while (queue.size() > 0) {
            for (int[] matrix : searchMatrix) {
                int x = queue.get(0).x + matrix[0];
                int y = queue.get(0).y + matrix[1];
                /* prevent out of picture bounds*/
                if ((x > 0 && y > 0) && (x < image.getWidth() && y < image.getHeight())) {
                    double pixLumi = getLuminance(image.getRGB(x, y));
                    Point p = new Point(x, y);
                    if (!isLuminancesEqual(pixLumi, getLuminance(bgColor))
                            && !silhouetteMap.containsKey(p)) {
                        queue.add(p);
                        silhouetteMap.put(p, image.getRGB(x, y));
                    }
                }
            }
            queue.remove(0);
        }
        return silhouetteMap;
    }

    /*******************************************************************************************************************
     * Compare luminances
     * @param pixLumi double first luminance value
     * @param bgLumi double second luminance value
     * @return true if  pixLumi in range bgLumi -/+ LUMINANCE_THRESHOLD
     */
    private static boolean isLuminancesEqual(double pixLumi, double bgLumi) {
        if ((pixLumi < bgLumi - LUMINANCE_THRESHOLD) || (pixLumi > bgLumi + LUMINANCE_THRESHOLD)) {
            return false;
        }
        return true;
    }

    /*******************************************************************************************************************
     * Method to get luminance of input color
     *
     * @param rgb int pixel color
     * @return double luminance of input color
     */
    private static double getLuminance(int rgb) {
        Color col = new Color(rgb);
        /* https://en.wikipedia.org/wiki/Relative_luminance */
        return 0.2126 * col.getRed() + 0.7152 * col.getGreen() + 0.0722 * col.getBlue();
    }

    /*******************************************************************************************************************
     *  Method to check if pixel belongs to silhouette
     *
     * @param x int x coordinate of current pixel
     * @param y int y coordinate of current pixel
     * @param silhouettes ArrayList of discovered silhouettes
     * @return boolean true, if pixel belongs to silhouette
     */
    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Integer>> silhouettes) {
        for (HashMap<Point, Integer> silhouette : silhouettes) {
            if (silhouette.containsKey(new Point(x, y))) {
                return true;
            }
        }
        return false;
    }

    /*******************************************************************************************************************
     * Method to read image from file
     *
     * @param filepath String file path/name
     * @return BufferedImage input image object
     */
    private static BufferedImage readImageFromFile(String filepath) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            System.out.println("Error: " + e + " " + filepath);
            img = null;
        }
        return img;
    }

    /**
     *
     * @param width
     * @param height
     * @param mapMass
     */
    private static void showDiscovered(int width, int height, ArrayList<HashMap<Point, Integer>> mapMass) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = bi.createGraphics();

        for (HashMap<Point, Integer> silhouette : silhouettes) {
            for (Point p : silhouette.keySet()) {
                gr.setColor(new Color(silhouette.get(p)));
                gr.drawLine(p.x, p.y, p.x, p.y);
            }
        }

        for (HashMap<Point, Integer> silhouette : mapMass) {
            gr.setColor(new Color((int) (Math.random() * 0x1000000)));

            for (Point p : silhouette.keySet()) {
                gr.drawLine(p.x, p.y, p.x, p.y);
            }
        }

        ImageIcon icon = new ImageIcon(bi);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(bi.getWidth(), bi.getHeight());
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
