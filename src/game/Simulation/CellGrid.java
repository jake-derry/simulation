package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;

public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;

    public CellGrid(Cell[][] cellGrid) {
        myCellGrid = cellGrid;
    }

    @Override
    public Iterator<Cell> iterator() {
        return null;
    }
}