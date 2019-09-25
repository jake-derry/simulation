package game.Simulation.Cell;

import java.util.Iterator;
import java.util.Map;

public class FireCell extends Cell {
    private static final int EMPTY = 0;
    private static final int BURNING = 1;
    private static final int TREE = 2;

    public FireCell(int state, Iterator<Cell> neighborIterator) {
        super(state, neighborIterator);
    }

    @Override
    public void updateNext() {
        Map<Integer, Integer> countMap = CellUtils.countMap(getNeighbors());

        switch (getState()) {
            case (EMPTY) : {
                
            }
            case (BURNING) : {

            }
            case (TREE) : {

            }
        }

    }
}
