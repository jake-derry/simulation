package util;

import game.Cell;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

    public static int countIf(int[] array, int search) {
        int count = 0;
        for (int value : array) {
            if (value == search) count++;
        }
        return count;
    }
}
