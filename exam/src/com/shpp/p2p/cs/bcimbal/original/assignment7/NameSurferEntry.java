package com.shpp.p2p.cs.bcimbal.original.assignment7;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

    private String name;
    private int [] rank;

	/* Constructor: NameSurferEntry(line) */

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        /* convert current string to arrayList*/
        ArrayList<String> sEntry = new ArrayList();
        while(line.contains(" ")) {
            sEntry.add(line.substring(0, line.indexOf(" ")));
            line = line.substring(line.indexOf(" ") + 1);
        }
        sEntry.add(line); // add last value
        /* get name */
        this.name = sEntry.get(0);
        sEntry.remove(0);
        /* get ranks*/
        rank = new int[sEntry.size()];
        for(int i = 0; i < sEntry.size(); i++)
            rank[i] = Integer.parseInt(sEntry.get(i));
    }

	/* Method: getName() */

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

	/* Method: getRank(decade) */

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        return rank[decade];
    }

	/* Method: toString() */

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int a:rank)  sb.append(a + " ");
        return name + " [" + sb.toString().trim() + "]";
    }
}

