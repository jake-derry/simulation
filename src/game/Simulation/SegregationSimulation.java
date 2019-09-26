package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This simulation runs a Segregation simulation which
 * has rules that simulate how people group together.
 * Only able to run a Segregation simulation and assumes that
 * the user doesn't want the cell grid to 'wrap around'
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Simulation
 * @author Jake Derry
 */
public class SegregationSimulation extends Simulation {
    private static final int GROUP_A = 1;
    private static final int GROUP_B = 2;

    private double satisfactionPercent;

    /**
     * Initializes a segregation simulation
     *
     * @param percent       Percent of same state cells required
     *                      for the cell to become 'satisfied'
     */
    public SegregationSimulation(String title, Cell[][] initialGrid, double percent) {
        super(title, initialGrid);
        satisfactionPercent = percent;
    }

    @Override
    protected void update() {
        List<Cell> unsatisfiedCells = findUnsatisfiedCells();
        updateEmptyCells(unsatisfiedCells);
    }

    private List<Cell> findUnsatisfiedCells() {
        List<Cell> unsatisfiedCells = new ArrayList<>();
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                if (! satisfied(i, j)) {
                    unsatisfiedCells.add(getCell(i, j));
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
        int randomIndex = random.nextInt(getEmptyCells().size());

        Cell randomEmptyCell = getEmptyCells().get(randomIndex);
        randomEmptyCell.setNextState(switchState);
        getEmptyCells().remove(randomEmptyCell);
        unsatisfiedCell.setNextState(EMPTY);
        getEmptyCells().add(unsatisfiedCell);
    }

    private boolean satisfied(int i, int j) {
        int[] neighborStates = getEightNeighborStates(i, j);
        double percentSame = getSameStateNeighbors(neighborStates, getCell(i, j).getState()) /
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
