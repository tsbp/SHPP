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

    public static void main(String[] args) {
        img = readImageFromFile("assets\\test2.bmp");
        int a = img.getWidth();
        int b = img.getHeight();
        int fon = img.getRGB(0, 0);
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (img.getRGB(i, j) != fon && !belongsTo(i, j, silhoettes)) {
                    /*if(!belongsTo(i, j, silhoettes))*/
                    {
                        HashMap<Point, Boolean> silhouett = discoverSilhoet(fon, i, j);
                        silhoettes.add(silhouett);
                    }
                }
            }
        }
        System.out.println("count: " + silhoettes.size());
    }

    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Boolean>> silh) {
        for (int i = 0; i < silh.size(); i++) {
            if (silh.get(i).containsKey(new Point(x, y))) {
                return true;
            }
        }
        return false;
    }

    private static HashMap<Point, Boolean> discoverSilhoet(int fon, int x, int y) {
        HashMap<Point, Boolean> tmp = new HashMap<>();

        dfs(x, y, fon, tmp);
        return tmp;
    }

    private static void dfs(int x, int y, int fon, HashMap<Point, Boolean> tmp) {
        tmp.put(new Point(x, y), true);
        for (int i = 0; i < searchMatrix.length; i++) {
            int _x = x + searchMatrix[i][0];
            int _y = y + searchMatrix[i][1];
            if (img.getRGB(_x, _y) != fon
                    && !tmp.containsKey(new Point(_x, _y))) {
                dfs(x + searchMatrix[i][0], y + searchMatrix[i][1], fon, tmp);
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
