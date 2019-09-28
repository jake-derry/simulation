package game.Simulation.Cell;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CellUtils {
    public static Map<Integer, Integer> countMap(Iterator<Cell> cellIterator) {
        Map<Integer, Integer> countMap = new HashMap<>();
        while (cellIterator.hasNext()) {
            int state = cellIterator.next().getState();
            int countValue;
            if (countMap.containsKey(state)) {
                countValue = countMap.get(state);
            }
            else {
                countMap.put(state, 0);
                countValue = countMap.get(state);
            }
            countMap.put(state, countValue++);
        }
        return countMap;
    }
}
