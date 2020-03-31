package com.shpp.p2p.cs.bcimbal.assignment14;


/*******************************************************************************************************************
 * The class makes
 */


public class Assignment14Part1 {


    static String fileIn = "";
    static String fileOut = "";

    public static void main(String[] args) {

        if (args.length > 0) {
            switch (args[0]) {
                case "-a":
                    getFileNames(args, Helper.EXTENSION_ARCH);
                    new Packer(fileIn, fileOut);
                    break;

                case "-u":
                    getFileNames(args, Helper.EXTENSION_UNARCH);
                    new Unpacker(fileIn, fileOut);
                    break;

                default:
                    process(args[0]);
            }
        } else {
            new Packer(Helper.DIR + "test.txt", Helper.DIR + "test.par");
        }

    }

    private static void process(String arg) {
        if (arg.contains(Helper.EXTENSION_ARCH)) {
            new Unpacker(
                    Helper.DIR + arg,
                    Helper.DIR + arg.replace(Helper.EXTENSION_ARCH, ""));
        } else {
            new Packer(Helper.DIR + arg, Helper.DIR + arg + Helper.EXTENSION_ARCH);
        }
    }

    private static void getFileNames(String[] args, String extension) {
        if (args.length > 1) {
            fileIn = Helper.DIR + args[1];
            if (args.length > 2) {
                fileOut = Helper.DIR + args[2];
            } else {
                fileOut = fileIn + extension;
            }
            return;
        }
        System.out.println("File not specified.");
    }
}
