package game.simulation;

import game.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This simulation runs the Segregation.xml Simulation which
 * has rules that demonstrate segregation.
 */
public class SegregationSimulation extends Simulation{
    public static final int GROUP_A = 1;
    public static final int GROUP_B = 2;

    private double satisfactionPercent;


    SegregationSimulation(String title, Cell[][] initialGrid, int windowSize, double percent) {
        super(title, initialGrid, windowSize);
        satisfactionPercent = percent;
    }

    @Override
    protected void update() {
        List<Cell> unsatisfiedCells = updateNonEmptyCells();
        updateEmptyCells(unsatisfiedCells);
    }

    private List<Cell> updateNonEmptyCells() {
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
        Cell randomEmptyCell = getEmptyCells().get(random.nextInt(getEmptyCells().size()));
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
