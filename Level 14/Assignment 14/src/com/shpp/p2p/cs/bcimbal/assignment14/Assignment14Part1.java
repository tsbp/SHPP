package com.shpp.p2p.cs.bcimbal.assignment14;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/*******************************************************************************************************************
 * The class makes archiveing
 */

public class Assignment14Part1 {
    public static void main(String args[]) {

        new Packer("assets/test.txt", "assets/arch.par");
        Unpacker unp = new Unpacker("assets/arch.par", "assets/arch.uar");
        String unpacked = new String(unp.getData());
        System.out.println(unpacked);
    }
}
