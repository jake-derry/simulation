package game.Simulation.Cell;

import game.Simulation.State;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CellUtils {
    public static Map<State, Integer> countMap(Iterator<Cell> cellIterator) {
        Map<State, Integer> countMap = new HashMap<>();
        while (cellIterator.hasNext()) {
            State state = cellIterator.next().getState();
            int countValue;
            if (! countMap.containsKey(state)) {
                countMap.put(state, 0);
            }
            countValue = countMap.get(state) + 1;
            countMap.put(state, countValue);
        }
        int x = 0;
        return countMap;
    }
}
