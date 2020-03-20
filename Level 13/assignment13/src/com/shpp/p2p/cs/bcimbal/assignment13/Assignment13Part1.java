package com.shpp.p2p.cs.bcimbal.assignment13;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assignment13Part1 extends JFrame {

    private static ArrayList<HashMap<Point, Boolean>> silhouettes = new ArrayList<>();

    private static final double LUMINANCE_THRESHOLD = 10;
    private static final double MIN_SILHOUETTE_SIZE = 100;
    //    -Xss128m


    /*******************************************************************************************************************
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
            filepath = "assets\\" + "animal.jpg";
            img = readImageFromFile(filepath);
        }

        if (img != null) {
            System.out.println("Silhouettes count (" + filepath + "): " + getSilhouettesCount(img));
        }

        showDiscovered(img);
    }


    /*******************************************************************************************************************
     * Method to count silhouettes
     *
     * @param img BufferedImage input image object
     * @return int silhouettes number
     */
    private static int getSilhouettesCount(BufferedImage img) {
//        /* define as background luminance at 0,0 */
        double bgLumi = getLuminance(img.getRGB(0, 0));

        /* proceed each pixel*/
        for (int x = 1; x < img.getWidth() - 1; x++) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                double pixLumi = getLuminance(img.getRGB(x, y));

                if (!isLuminancesEqual(pixLumi, bgLumi) /*&& !belongsTo(x, y, silhouettes)*/) {
                    /* found a pixel with luminance not equal background */
                    if (!belongsTo(x, y, silhouettes)) {
                        HashMap<Point, Boolean> silhouette = discoverSilhouette(img, bgLumi, x, y);
                        if (silhouette.size() >= MIN_SILHOUETTE_SIZE) {
                            silhouettes.add(silhouette);
                        }
                    }
                }
//                else
//                    bgLumi = getLuminance(img.getRGB(x, y));
            }
        }
        return silhouettes.size();
    }

    /* Array to perform searching around pixel */
    static int[][] searchMatrix = {{1, 0}, /*{1, 1},*/ {0, 1}, /*{-1, 1},*/ {-1, 0}, /*{-1, -1},*/ {0, -1}/*, {1, -1}*/};

    /*******************************************************************************************************************
     * Method to discover silhouette
     *
     * @param image BufferedImage input image object
     * @param bgLumi int background luminance
     * @param xC int x coordinate of current pixel
     * @param yC int y coordinate of current pixel
     * @return HashMap of discovered silhouette
     */
    private static HashMap<Point, Boolean> discoverSilhouette(BufferedImage image, double bgLumi, int xC, int yC) {
        HashMap<Point, Boolean> silhouetteMap = new HashMap<>();
        silhouetteMap.put(new Point(xC, yC), true);

        /* breadth-first search, BFS */
        ArrayList<Point> queue = new ArrayList<>();
        queue.add(new Point(xC, yC));
        while (queue.size() > 0) {
            for (int[] matrix : searchMatrix) {
                int x = queue.get(0).x + matrix[0];
                int y = queue.get(0).y + matrix[1];
                /* prevent out of picture bounds*/
                if ((x > 0 && y > 0) && (x < image.getWidth() && y < image.getHeight())) {
                    double pixLumi = getLuminance(image.getRGB(x, y));
                    if (!isLuminancesEqual(pixLumi, bgLumi)
                            && !silhouetteMap.containsKey(new Point(x, y))) {
                        Point p = new Point(x, y);
                        queue.add(p);
                        silhouetteMap.put(p, true);
                    }
                }
            }
            queue.remove(0);
        }
        return silhouetteMap;
    }

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
    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Boolean>> silhouettes) {
        for (HashMap<Point, Boolean> silhouette : silhouettes) {
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

    private static  void showDiscovered(BufferedImage img) {
        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = bi.createGraphics();
        for (HashMap<Point, Boolean> silhouette : silhouettes) {
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
