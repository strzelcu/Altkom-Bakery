package com.company.pastries;

import com.company.utilities.Randomize;

public enum Size {
    /**
     * Możemy dodawać tutaj dowolny rozmiar dla potrzeb rozrostu rozmiarów i asortymentu cukierni
     */
    BIG, SMALL;

    private static Size[] sizes = {
            BIG,
            SMALL
    };

    public static Size getRandomSize() {
        return sizes[Randomize.nextInt(sizes.length)];
    }
}
