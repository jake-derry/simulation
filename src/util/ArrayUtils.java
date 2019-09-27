package util;

import game.Simulation.State;

public class ArrayUtils {

    public static int countIf(State[] array, State search) {
        int count = 0;
        for (int value : array) {
            if (value == search) count++;
        }
        return count;
    }
}
