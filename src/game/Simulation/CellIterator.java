package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Iterator of cells used in multiple classes
 */
public class CellIterator implements Iterator<Cell> {
    private Queue<Cell> myCellQueue;

    /**
     * Constructor of CellIterator that sets the cell grid
     * of the iterator
     *
     * @param cellGrid      Grid of cells
     */
    CellIterator(Cell[][] cellGrid) {
        myCellQueue = new LinkedList<>();
        for(Cell[] cellRow : cellGrid) {
            for (Cell cell : cellRow){
                myCellQueue.add(cell);
            }
        }
    }

    /**
     * Constructor of CellIterator that sets a list of cells
     * as the queue of the iterator
     * @param cells         List of cells
     */
    CellIterator(List<Cell> cells) {
        myCellQueue = new LinkedList<>(cells);
    }

    @Override
    public boolean hasNext() {
        return !myCellQueue.isEmpty();
    }

    @Override
    public Cell next() {
        return myCellQueue.remove();
    }

}
