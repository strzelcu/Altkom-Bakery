package com.company.utilities;

import java.util.Random;

public class Randomize {

    private static Random generator = new Random();

    public static int nextInt(int bound){
        return generator.nextInt(bound);
    }

    public static boolean nextBoolean(){
        return generator.nextBoolean();
    }

}
