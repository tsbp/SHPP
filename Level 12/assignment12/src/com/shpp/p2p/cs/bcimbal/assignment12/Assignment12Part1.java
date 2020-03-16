package com.shpp.p2p.cs.bcimbal.assignment12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Assignment12Part1 {

    private static ArrayList<HashMap<Point, Boolean>> silhoettes = new ArrayList<>();
    private static BufferedImage img;

    private static final double THRES = 50;

    public static void main(String[] args) {
        img = readImageFromFile("assets\\test2.jpg");
        int a = img.getWidth();
        int b = img.getHeight();
        int fon = img.getRGB(0, 0);
        double lumFon = getLuminance(fon);
        // double l = lum;
        for (int i = 1; i < a -1; i++) {
            for (int j = 1; j < b - 1; j++) {
                double curLum = getLuminance(img.getRGB(i, j));

                if ((lumFon > curLum + THRES) && !belongsTo(i, j, silhoettes)) {
                    {
                        HashMap<Point, Boolean> silhouett = discoverSilhoet(lumFon, i, j);
                        //if (silhouett.size() > 200)
                            silhoettes.add(silhouett);
                    }
                }
            }
        }
        //Y = 0.2126 * R + 0.7152 * G + 0.0722 * B
        System.out.println("count: " + silhoettes.size());
    }

    private static double getLuminance(int rgb) {
        Color col = new Color(rgb);
        double a = 0.2126 * col.getRed() + 0.7152 * col.getGreen() + 0.0722 * col.getBlue();
        return a;
    }

    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Boolean>> silh) {
        for (int i = 0; i < silh.size(); i++) {
            if (silh.get(i).containsKey(new Point(x, y))) {
                return true;
            }
        }
        return false;
    }

    private static HashMap<Point, Boolean> discoverSilhoet(double fon, int x, int y) {
        HashMap<Point, Boolean> tmp = new HashMap<>();
        dfs(x, y, fon, tmp);
        return tmp;
    }

//    -Xss1024m

    private static void dfs(int x, int y, double fon, HashMap<Point, Boolean> tmp) {
        tmp.put(new Point(x, y), true);
        for (int i = 0; i < searchMatrix.length; i++) {
            int _x = x + searchMatrix[i][0];
            int _y = y + searchMatrix[i][1];
            if ((_x > 0 && _y > 0) && (_x < img.getWidth() && _y < img.getHeight())) {

                double curLum = getLuminance(img.getRGB(_x, _y));
                if (/*img.getRGB(_x, _y) != fon*/(fon > curLum + THRES)
                        && !tmp.containsKey(new Point(_x, _y))) {
                    dfs(_x, _y, fon, tmp);
                }
            }
        }
    }

    static int[][] searchMatrix = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    private static BufferedImage readImageFromFile(String file) {
        BufferedImage img = null;
        try {
            File input_file = new File(file);
            img = new BufferedImage(1, 1,
                    BufferedImage.TYPE_INT_ARGB);
            img = ImageIO.read(input_file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return img;
    }
}
