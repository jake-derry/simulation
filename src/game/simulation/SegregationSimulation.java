package game.simulation;

import game.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private double satisfactionPercent;


    SegregationSimulation(String title, Cell[][] initialGrid, int windowSize, double percent) {
        super(title, initialGrid, windowSize);
        emptyCells = findEmptyCells();
        satisfactionPercent = percent;
    }

    private List<Cell> findEmptyCells() {
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
        Cell randomEmptyCell = emptyCells.get(random.nextInt(emptyCells.size()));
        randomEmptyCell.setNextState(switchState);
        emptyCells.remove(randomEmptyCell);
        unsatisfiedCell.setNextState(EMPTY);
        emptyCells.add(unsatisfiedCell);
    }

    private boolean satisfied(int i, int j) {
        int[] neighborStates = getNeighborStates(i, j);
        // TODO: Implement satisfied

        return true;
    }
}
