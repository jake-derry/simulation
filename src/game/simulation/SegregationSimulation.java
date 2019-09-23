package game.simulation;

import game.Cell;
import util.ArrayUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This simulation runs a Segregation simulation which
 * has rules that simulate how people group together.
 * Only able to run a Segregation simulation and assumes that
 * the user wants the cell grid to 'wrap around.'
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Simulation
 * @author Jake Derry
 */
public class SegregationSimulation extends Simulation {
    private static final boolean WRAP_AROUND = true;
    private static final int GROUP_A = 1;
    private static final int GROUP_B = 2;

    private double satisfactionPercent;
    private int nonEmptyCount;

    /**
     * Initializes a segregation simulation
     *
     * @param percent       Percent of same state cells required
     *                      for the cell to become 'satisfied'
     */
    public SegregationSimulation(String title, Cell[][] initialGrid, int windowSize, double percent) {
        super(title, initialGrid, windowSize);
        satisfactionPercent = percent;
        nonEmptyCount = countNonEmpty();
    }

    private int countNonEmpty() {
        int count = 0;
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                if (getCell(i, j).getState() != EMPTY) count++;
            }
        }
        return count;
    }

    @Override
    protected void update() {
        List<Cell> unsatisfiedCells = updateNonEmptyCells();
        updateEmptyCells(unsatisfiedCells);
        nonEmptyCount = countNonEmpty();
    }

    private List<Cell> updateNonEmptyCells() {
        List<Cell> unsatisfiedCells = new ArrayList<>();
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                if (! satisfied(i, j)) {
                    unsatisfiedCells.add(getCell(i, j, WRAP_AROUND));
                }
            }
        }
        return unsatisfiedCells;
    }

    private void updateEmptyCells(List<Cell> unsatisfiedCells) {
        for (Cell unsatisfiedCell : unsatisfiedCells) {
            switchEmptyCell(unsatisfiedCell);
        }
    }

    private void switchEmptyCell(Cell unsatisfiedCell) {
        Random random = new Random();
        int switchState = unsatisfiedCell.getState();
        Cell randomEmptyCell = getEmptyCells().get(random.nextInt(getEmptyCells().size()));
        randomEmptyCell.setNextState(switchState);
        getEmptyCells().remove(randomEmptyCell);
        unsatisfiedCell.setNextState(EMPTY);
        getEmptyCells().add(unsatisfiedCell);
    }

    private boolean satisfied(int i, int j) {
        int[] neighborStates = getEightNeighborStates(i, j);
        double percentSame = getSameStateNeighbors(neighborStates, getCell(i, j, WRAP_AROUND).getState()) /
                (double) neighborStates.length;
        return percentSame >= satisfactionPercent;
    }

    private int getSameStateNeighbors(int[] neighborStates, int state) {
        int count = 0;
        for (int neighborState: neighborStates) {
            if (neighborState == state) {
                count++;
            }
        }
        return count;
    }
}
