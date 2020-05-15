package com.shpp.p2p.cs.bcimbal.assignment15;

/* Class makes file compression/decompression by Huffman codding */

public class Assignment15Part1 extends CHelper {

    static String fileIn = "";
    static String fileOut = "";

    /*******************************************************************************************************************
     * Realize main function of class
     * @param args array of strings program arguments
     */
    public static void main(String[] args) {

        if (args.length > 0) {
            switch (args[0]) {
                case "-a":
                    getFileNames(args, EXTENSION_ARCH);
                    new Compressor(fileIn, fileOut, COMPRESSOR_MODE_COMPRESS);
                    break;

                case "-u":
                    getFileNames(args, EXTENSION_UNARCH);
                    new Compressor(fileIn, fileOut, COMPRESSOR_MODE_DECOMPRESS);
                    break;

                default: // one parameter set
                    process(args[0]);
            }
        } else {
            /* empty arguments */
            new Compressor(DIR + "test.txt", DIR + "test.par", COMPRESSOR_MODE_COMPRESS);
        }
    }

    /*******************************************************************************************************************
     * Archiving/unarchiving with one parameter set
     *
     * @param file String file name
     */
    private static void process(String file) {
        if (file.contains(EXTENSION_ARCH)) {
            new Compressor(
                    DIR + file,
                    DIR + file.replace(EXTENSION_ARCH, ""), COMPRESSOR_MODE_DECOMPRESS);
        } else {
            new Compressor(DIR + file, DIR + file + EXTENSION_ARCH, COMPRESSOR_MODE_COMPRESS);
        }
    }

    /*******************************************************************************************************************
     * Get file names from program arguments
     *
     * @param args array of strings program arguments
     * @param extension String file extension to add
     */
    private static void getFileNames(String[] args, String extension) {
        if (args.length > 1) {
            fileIn = DIR + args[1];
            if (args.length > 2) {
                fileOut = DIR + args[2];
            } else {
                fileOut = fileIn + extension;
            }
            return;
        }
        System.out.println("File not specified.");
    }

}


