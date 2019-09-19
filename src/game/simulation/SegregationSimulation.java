package game.simulation;

import game.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * This simulation runs the Segregation Simulation which
 * has rules that demonstrate segregation.
 */
public class SegregationSimulation extends Simulation{
    public static final int EMPTY = 0;
    public static final int GROUP_A = 1;
    public static final int GROUP_B = 2;

    /**
     * The number of cells with the empty code.
     */
    private List<Cell> emptyCells;


    SegregationSimulation(String title, Cell[][] initialGrid, int windowSize) {
        super(title, initialGrid, windowSize);
        emptyCells = getEmptyCells();
    }

    private List<Cell> getEmptyCells() {
        List<Cell> emptyCellList = new ArrayList<>();
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                if (getCell(i, j).getState() == EMPTY) {
                    emptyCellList.add(getCell(i, j));
                }
            }
        }
        return emptyCellList;
    }


    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                getCell(i, j).setNextState(nextState(i, j));
            }
        }
    }

    private int nextState(int i, int j) {
        if (getCell(i, j).getState() == EMPTY) {
            // handle randomly placing a 'person' in a spot
            return EMPTY;
        }
        else {
            if (satisfied(i, j)) {
                return getCell(i, j).getState();
            }

            else {
                return EMPTY;
            }
        }
    }

    private boolean satisfied(int i, int j) {
        int[] neighborStates = getNeighborStates(i, j);
        // TODO: implement satisfied

        return true;
    }
}
