package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class CellIterator implements Iterator<Cell> {
    private Queue<Cell> myCellQueue;

    /**
     * Constructor of CellIterator that sets the
     * cell grid of the iterator
     *
     * @param cellGrid      grid of cells
     */
    CellIterator(Cell[][] cellGrid) {
        myCellQueue = new LinkedList<>();
        for(Cell[] cellRow : cellGrid) {
            for (Cell cell : cellRow){
                myCellQueue.add(cell);
            }
        }
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
