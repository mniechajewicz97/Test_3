package org.example;

import java.util.Random;

public class ArraySumThreads {

    public static int[] createArray() {
        Random random = new Random();

        int[] array = new int[200000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    public static long sumArrayElements(int[] array) {
        long count = 0;
        for (int i = 0; i < array.length; i++) {
            count += array[i];
        }
        return count;
    }

}

