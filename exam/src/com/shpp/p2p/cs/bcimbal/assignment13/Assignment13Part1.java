package com.shpp.p2p.cs.bcimbal.assignment13;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
import com.shpp.p2p.cs.bcimbal.assignment16.ArrList;
import com.shpp.p2p.cs.bcimbal.assignment17.HHashMap;
import com.shpp.p2p.cs.bcimbal.assignment16.LinkList;

/*******************************************************************************************************************
 * The class counts the number of silhouettes in the picture
 * by the method "breadth-first search, BFS" without recursion
 */

public class Assignment13Part1 {

    /* The threshold of luminance between silhouettes and background*/
    private static final double LUMINANCE_THRESHOLD = 20;
    /* minimum silhouette size as a percentage of the image size */
    private static final double MIN_SILHOUETTE_SIZE = 0.1;
    /* contour depth */
    private static final int CONTOUR_DEPTH = 1;
    /* Array to perform searching around pixel */
    private static final int[][] searchMatrix = {
            {1, 0}, {1, 1}, {0, 1}, {-1, 1},
            {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    /*******************************************************************************************************************
     * Makes counting of silhouettes in input image (file name as argument)
     * if argument not set, tries to open "test.jpg"
     *
     * @param args String program arguments
     */
    public static void main(String[] args) {

        BufferedImage img = getImageFromArgs(args);

        if (img != null) {
            /* check if image has alpha channel */
            if (img.getColorModel().hasAlpha()) {
                System.out.print("Has alpha. ");
                img = removeAlpha(img);
            }
            /* count silhouettes in original image*/
            ArrList<HHashMap<Point, Integer>> silhouettes = getSilhouettes(img);
            System.out.print("Silhouettes count: " + silhouettes.size());

            ArrList<HHashMap<Point, Integer>> contours = new ArrList<>();
            if(CONTOUR_DEPTH > 0) {
                /* outline silhouettes (pseudo unsticking)*/
                countourize(silhouettes, contours, CONTOUR_DEPTH);
                /* count silhouette after pseudo unsticking */
                silhouettes = getSilhouettes(getImageFrom(silhouettes, img.getWidth(), img.getHeight()));
                System.out.println(". After unsticking: " + silhouettes.size());
            }
            /* visualization of the result */
            showDiscovered(img.getWidth(), img.getHeight(), contours, silhouettes);
        }
    }

    /*******************************************************************************************************************
     * Read image from file (as input arguments), if not possible try to open default test.jpg
     *
     * @param args program arguments
     * @return BufferedImage as result of reading a file (null, if not possible to read)
     */
    private static BufferedImage getImageFromArgs(String[] args) {
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
        System.out.print("(" + filepath + ") - ");
        return img;
    }

    /*******************************************************************************************************************
     * Create image from input maps
     *
     * @param silhouettes ArrayList of silhouettes
     * @param width output image width
     * @param height output image height
     * @return BufferedImage image
     */
    private static BufferedImage getImageFrom(ArrList<HHashMap<Point, Integer>> silhouettes, int width, int height) {
        BufferedImage tmpImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gr = tmpImage.createGraphics();

        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, width, height);
        for (HHashMap<Point, Integer> silhouette : silhouettes) {

            for (Point p : silhouette.keySet()) {
                gr.setColor(new Color(silhouette.get(p)));
                gr.drawLine(p.x, p.y, p.x, p.y);
            }
        }
        gr.dispose();
        return tmpImage;
    }


    /*******************************************************************************************************************
     * Remove alpha channel from image
     *
     * @param img image to remove alpha channel
     * @return image without alpha channel
     */
    private static BufferedImage removeAlpha(BufferedImage img) {
        BufferedImage nonAlpha = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D tmp = nonAlpha.createGraphics();
        tmp.setColor(Color.WHITE);
        tmp.fillRect(0, 0, nonAlpha.getWidth(), nonAlpha.getHeight());
        tmp.drawImage(img, 0, 0, null);
        tmp.dispose();
        return nonAlpha;
    }

    /*******************************************************************************************************************
     * Get outlines of input silhouettes
     *
     * @param silhouettes ArrayList of  silhouettes
     * @param contours ArrayList of  contours
     * @param contourDepth int number of iterations of contourizing
     */
    private static void countourize(ArrList<HHashMap<Point, Integer>> silhouettes,
                                    ArrList<HHashMap<Point, Integer>> contours,
                                    int contourDepth) {
        for (int n = 0; n < contourDepth; n++) {
            boolean first = false;
            if (contours.size() <= 0) {
                first = true;
            }

            for (int i = 0; i < silhouettes.size(); i++) {
                if (first) {
                    contours.add(getContour(silhouettes.get(i), new HHashMap<>()));
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
    private static void substrate(ArrList<HHashMap<Point, Integer>> contours,
                                  ArrList<HHashMap<Point, Integer>> silhouettes) {
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
    private static HHashMap<Point, Integer> getContour(HHashMap<Point, Integer> silh,
                                                      HHashMap<Point, Integer> contr) {
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
    private static ArrList<HHashMap<Point, Integer>> getSilhouettes(BufferedImage img) {
        ArrList<HHashMap<Point, Integer>> tmpSilhouettes = new ArrList<>();
        /* define as background luminance at bottom right pixel */
        int bgColor = img.getRGB(img.getWidth() - 1, img.getHeight() - 1);
        int minSilhSize = getMinSilhouetteSize(img.getWidth(), img.getHeight());

        /* proceed each pixel*/
        for (int x = 1; x < img.getWidth() - 1; x++) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                double pixLumi = getLuminance(img.getRGB(x, y));
                if (!isLuminancesEqual(pixLumi, getLuminance(bgColor)) && !belongsTo(x, y, tmpSilhouettes)) {
                    /* found a pixel with luminance not equal background */
                    HHashMap<Point, Integer> silhouette = discoverSilhouette(img, bgColor, x, y);
                    if (silhouette.size() >= minSilhSize) {
                        tmpSilhouettes.add(silhouette);
                    }
                }
//                else
//                    bgColor = img.getRGB(x, y);
            }
        }
        return tmpSilhouettes;
    }

    /*******************************************************************************************************************
     * Get minimum silhouette size
     *
     * @param width  image width
     * @param height image height
     * @return minimum silhouette size as a predefined percentage of the image size
     */
    private static int getMinSilhouetteSize(int width, int height) {
        return (int)(((width * height) / 100) * MIN_SILHOUETTE_SIZE);
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
    private static HHashMap<Point, Integer> discoverSilhouette(BufferedImage image, int bgColor, int xC, int yC) {
        HHashMap<Point, Integer> silhouetteMap = new HHashMap<>();
        silhouetteMap.put(new Point(xC, yC), image.getRGB(xC, yC));
        double bgLumi = getLuminance(bgColor);

        /* breadth-first search, BFS */
        LinkList<Point> queue = new LinkList<>();
        queue.add(new Point(xC, yC));
        while (queue.size() > 0) {
            Point currentPixel = queue.poll();
            for (int[] matrix : searchMatrix) {
                int x = currentPixel.x + matrix[0];
                int y = currentPixel.y + matrix[1];
                /* prevent out of picture bounds*/
                if ((x > 0 && y > 0) && (x < image.getWidth() && y < image.getHeight())) {
                    double pixLumi = getLuminance(image.getRGB(x, y));
                    Point p = new Point(x, y);
                    if (!isLuminancesEqual(pixLumi, bgLumi)
                            && !silhouetteMap.containsKey(p)) {
                        silhouetteMap.put(p, image.getRGB(x, y));
                        queue.addLast(p);    //TODO replace to queue.addFirst(p) to use Depth-first search algorithm
                    }
                }
            }
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
    private static boolean belongsTo(int x, int y, ArrList<HHashMap<Point, Integer>> silhouettes) {
        for (HHashMap<Point, Integer> silhouette : silhouettes) {
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

    /*******************************************************************************************************************
     * Visualise proceeded images
     *
     * @param width int image width
     * @param height int image width
     * @param map1 list of images maps
     * @param map2 list of images maps
     */
    private static void showDiscovered(int width, int height,
                                       ArrList<HHashMap<Point, Integer>> map1,
                                       ArrList<HHashMap<Point, Integer>> map2) {
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics gr = bi.createGraphics();

        gr.setColor(Color.WHITE);
        gr.fillRect(0, 0, width, height);

        for (HHashMap<Point, Integer> silhouette : map1) {
            gr.setColor(new Color((int) (Math.random() * 0x1000000)));

            for (Point p : silhouette.keySet()) {
                gr.drawLine(p.x, p.y, p.x, p.y);
            }
        }

        for (int i = 0; i < map2.size(); i++) {
            //gr.setColor(new Color((int) (Math.random() * 0x1000000)));
            for (Point p : map2.get(i).keySet()) {
                gr.setColor(new Color(map2.get(i).get(p)));
                gr.drawLine(p.x, p.y, p.x, p.y);

            }
            Point cp = getCenterPoint(map2.get(i));
            gr.setColor(Color.green);
            gr.setFont(new Font("Courier", 0, 40));
            gr.drawString("" + (i + 1), cp.x, cp.y);
        }
        gr.dispose();

        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(bi.getWidth() + 30, bi.getHeight() + 100);
        JLabel lbl = new JLabel();
        lbl.setIcon(new ImageIcon(bi));
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*******************************************************************************************************************
     * Find center point of image map
     * @param map image map
     * @return Point center point
     */
    private static Point getCenterPoint(HHashMap<Point, Integer> map) {
        int maxx = 0;
        int maxy = 0;
        int minx = 100000;
        int miny = 100000;
        for (Point p : map.keySet()) {
            if (p.x > maxx) maxx = p.x;
            if (p.x < minx) minx = p.x;
            if (p.y > maxy) maxy = p.y;
            if (p.y < miny) miny = p.y;
        }
        return new Point((maxx + minx) / 2, (maxy + miny) / 2);
    }
}
