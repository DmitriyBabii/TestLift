package models;

import java.util.Random;

public class Randomize {

    private static final Random rand = new Random();

    public static int getRandomBetween(int from, int to) {
        return from + rand.nextInt(to - from + 1);
    }
}
