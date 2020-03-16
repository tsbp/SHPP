package com.shpp.p2p.cs.bcimbal.assignment12;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Assignment12Part1 {

    private static ArrayList<HashMap<Point, Boolean>> silhouettes = new ArrayList<>();

    private static final double LUMINANCE_THRESHOLD = 50;
    private static final double MIN_SILHOUETTE_SIZE = 10;
    //    -Xss128m

    public static void main(String[] args) {
        String filepath = "assets\\";
        if(args.length > 0)
            filepath = filepath + args[0];
        else
            filepath = filepath + "test.jpg";
        BufferedImage img = readImageFromFile(filepath);
        System.out.println("Silhoettes count (" + filepath + "): " + getSilhouettesCount(img));
    }

    private static int getSilhouettesCount(BufferedImage img) {
        /* define background luminance at 0,0 */
        double bgLuminance = getLuminance(img.getRGB(0, 0));
        /* proceed each pixel*/
        for (int x = 1; x < img.getWidth() - 1; x++) {
            for (int y = 1; y < img.getHeight() - 1; y++) {
                double curLum = getLuminance(img.getRGB(x, y));
                if ((bgLuminance > curLum + LUMINANCE_THRESHOLD) && !belongsTo(x, y, silhouettes)) {
                    HashMap<Point, Boolean> silhouett = discoverSilhoet(img, bgLuminance, x, y);
                    if (silhouett.size() >= MIN_SILHOUETTE_SIZE)
                        silhouettes.add(silhouett);
                }
            }
        }
        return silhouettes.size();
    }

    private static double getLuminance(int rgb) {
        Color col = new Color(rgb);
        /* https://en.wikipedia.org/wiki/Relative_luminance */
        return 0.2126 * col.getRed() + 0.7152 * col.getGreen() + 0.0722 * col.getBlue();
    }

    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Boolean>> silhouettes) {
        for (HashMap<Point, Boolean> silhouet : silhouettes) {
            if (silhouet.containsKey(new Point(x, y))) {
                return true;
            }
        }
        return false;
    }

    private static HashMap<Point, Boolean> discoverSilhoet(BufferedImage image, double fon, int x, int y) {
        HashMap<Point, Boolean> tmp = new HashMap<>();
        dfs(x, y, image, fon, tmp);
        return tmp;
    }

    static int[][] searchMatrix = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    private static void dfs(int coordX, int coordY, BufferedImage image, double fon, HashMap<Point, Boolean> map) {
        map.put(new Point(coordX, coordY), true);
        /* search unmarked pixels around current pixel*/
        for (int i = 0; i < searchMatrix.length; i++) {
            int x = coordX + searchMatrix[i][0];
            int y = coordY + searchMatrix[i][1];
            /* prevent out of picture bounds*/
            if ((x > 0 && y > 0) && (x < image.getWidth() && y < image.getHeight())) {
                double curLum = getLuminance(image.getRGB(x, y));
                if ((fon > curLum + LUMINANCE_THRESHOLD)
                        && !map.containsKey(new Point(x, y))) {
                    dfs(x, y, image, fon, map);
                }
            }
        }
    }

    private static BufferedImage readImageFromFile(String filepath) {
        BufferedImage img = null;
        try {
            File input_file = new File(filepath);
            img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            img = ImageIO.read(input_file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return img;
    }
}
