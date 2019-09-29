package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;

public class CellIterator implements Iterator<Cell> {
    private Cell[][] myCellGrid;
    private int currentRow;
    private int currentColumn;

    /**
     * Constructor of CellIterator that sets the
     * cell grid of the iterator
     *
     * @param cellGrid      grid of cells
     */
    CellIterator(Cell[][] cellGrid) {
        myCellGrid = cellGrid;
    }


    @Override
    public boolean hasNext() {
        boolean rowInRange = currentRow + 1 < myCellGrid.length && currentRow + 1 >= 0;
        boolean columnInRange = currentColumn + 1 < myCellGrid[0].length && currentColumn + 1 >= 0;
        return (rowInRange && columnInRange);
    }

    @Override
    public Cell next() {
        currentRow++; currentColumn++;
        return myCellGrid[currentRow][currentColumn];
    }

}
