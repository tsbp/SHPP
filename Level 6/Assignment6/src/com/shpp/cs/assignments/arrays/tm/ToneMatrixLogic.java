package com.shpp.cs.assignments.arrays.tm;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];

        for (int row = 0; row < toneMatrix.length; row++) {
            if (toneMatrix[row][column]) {
                /* sum */
                for (int value = 0; value < result.length; value++) {
                    result[value] += samples[row][value];
                }
                /* max */
                double max = 1; // 1 to avoid division by zero
                for (double value : result) {
                    if (Math.abs(max) < Math.abs(value)) {
                        max = value;
                    }
                }
                /* normalize*/
                for (int k = 0; k < result.length; k++) {
                    result[k] /= max;
                }
            }
        }

        return result;
    }
}
