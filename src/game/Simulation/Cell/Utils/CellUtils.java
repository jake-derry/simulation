package game.Simulation.Cell.Utils;

import game.Simulation.Cell.Cell;
import game.Simulation.State;

import java.util.*;

/**
 * General util functions that are helpful only with the Cell class.
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Cell
 */
public class CellUtils {

    /**
     * Iterates through an Iterable of Cells to produce a map
     * of each state and the number of cells of that state.
     *
     * @param cells         An Iterable of Cells
     * @return              Map with state keys and the number
     *                      of cells of that state as values
     */
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
        return countMap;
    }

    /**
     * Iterates through an Iterable of Cells to produce a map of each
     * state and a list of cells of that state.
     *
     * @param cells         An Iterable of Cells
     * @return              Map with state keys and the cells of that
     *                      state as values
     */
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
