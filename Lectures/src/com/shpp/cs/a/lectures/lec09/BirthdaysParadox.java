package com.shpp.cs.a.lectures.lec09;

import acm.util.RandomGenerator;
import com.shpp.cs.a.console.TextProgram;

public class BirthdaysParadox extends TextProgram {

    @Override
    public void run() {
        RandomGenerator r = RandomGenerator.getInstance();

        int[] days = new int[365];

        int skolko = 36;

        boolean found = false;
        while (!found) {
            int day = r.nextInt(365);
            skolko++;
            if (days[day] != 2) {
                days[day]++;
            } else {
                found = true;
            }
        }

        println("skolko? haha: "+skolko);

    }


}
