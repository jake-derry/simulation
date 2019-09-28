package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;
import java.util.Map;

public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;

    public CellGrid(Map<String, Object> parameterMap, int[][] cellGrid) {
    }

    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(myCellGrid);
    }
}