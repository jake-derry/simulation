package game.Simulation.Cell.Utils;

import game.Simulation.Cell.Cell;
import game.Simulation.State;

import java.util.*;

public class CellUtils {
    public static Map<State, Integer> countMap(Iterable<Cell> cells) {
        Map<State, Integer> countMap = new HashMap<>();
        for (Cell cell : cells) {
            State state = cell.getState();
            if (! countMap.containsKey(state)) {
                countMap.put(state, 0);
            }
            int countValue = countMap.get(state) + 1;
            countMap.put(state, countValue);
        }
        addAllState(countMap);
        return countMap;
    }

    private static void addAllState(Map<State, Integer> countMap) {
        for (State state : State.values()) {
            countMap.put(state, 0);
        }
    }

    public static Map<State, List<Cell>> cellMap(Iterable<Cell> cells) {
        Map<State, List<Cell>> countMap = new HashMap<>();
        for (Cell cell : cells) {
            State state = cell.getState();
            if (! countMap.containsKey(state)) {
                countMap.put(state, new ArrayList<Cell>());
            }
            countMap.get(state).add(cell);
        }
        return countMap;
    }
}
