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

    public static void main(String[] args) {
        BufferedImage img = readImageFromFile("assets\\test.png");
        int a = img.getWidth();
        int b = img.getHeight();
        int fon = img.getRGB(0, 0 );
        for(int i = 0; i < a; a++) {
            for(int j = 0; j < b; j++) {
                if(img.getRGB(i, j ) != fon  && !belongsTo(i, j, silhoettes)) {
                    HashMap<Point, Boolean> silhouett = searchForSilhoet(fon, i, j);
                    silhoettes.add(silhouett);
                }
            }
        }
    }

    private static boolean belongsTo(int x, int y, ArrayList<HashMap<Point, Boolean>> silh) {
        for(int i = 0; i < silh.size(); i++) {
            if(silh.get(i).containsKey(new Point(x, y))){
                return true;
            }
        }
        return false;
    }

    private static HashMap<Point, Boolean> searchForSilhoet(int fon, int i, int j) {
        //dfs(i, j);
        return null;
    }

    static int [][] searchMatrix = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

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
        return  img;
    }
}
