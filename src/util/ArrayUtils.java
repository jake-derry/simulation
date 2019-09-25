package util;

public class ArrayUtils {

    public static int countIf(int[] array, int search) {
        int count = 0;
        for (int value : array) {
            if (value == search) count++;
        }
        return count;
    }
}
