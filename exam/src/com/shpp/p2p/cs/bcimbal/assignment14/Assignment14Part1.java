package com.shpp.p2p.cs.bcimbal.assignment14;


/*******************************************************************************************************************
 * The class makes files archiving/unarchiving using flags -a/-u
 * or depending file extension
 */

public class Assignment14Part1 {

    static String fileIn = "";
    static String fileOut = "";

    /*******************************************************************************************************************
     * Realize main function of class
     *
     * @param args array of strings program arguments
     */
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

                default: // one parameter set
                    process(args[0]);
            }
        } else {
            /* empty arguments */
            new Packer(Helper.DIR + "test.txt", Helper.DIR + "test.par");
        }

    }

    /*******************************************************************************************************************
     * Archiving/unarchiving with one parameter set
     *
     * @param file String file name
     */
    private static void process(String file) {
        if (file.contains(Helper.EXTENSION_ARCH)) {
            new Unpacker(
                    Helper.DIR + file,
                    Helper.DIR + file.replace(Helper.EXTENSION_ARCH, ""));
        } else {
            new Packer(Helper.DIR + file, Helper.DIR + file + Helper.EXTENSION_ARCH);
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
