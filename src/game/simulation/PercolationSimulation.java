package game.simulation;

import game.Cell;

/**
 * This simulation runs a Percolation simulation which
 * has rules that simulate how water percolates through
 * a semi-porous solid . Only able to run a Percolation
 * simulation and assumes that the user does not want
 * the cell grid to 'wrap around.'
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Simulation
 * @author Jake Derry
 */
public class PercolationSimulation extends Simulation {
    private static final int WALL = 1;
    private static final int WATER = 2;

    /**
     * Initializes a percolation simulation
     */
    public PercolationSimulation(String title, Cell[][] initialGrid, int windowSize) {
        super(title, initialGrid, windowSize);
    }

    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                getCell(i, j).setNextState(percolate(i, j));
            }
        }
    }

    private int percolate(int i, int j) {
        int state = getCell(i, j).getState();
        if (state != EMPTY) {
            return state;
        }
        return wetNeighbor(getEightNeighborStates(i, j));
    }

    private int wetNeighbor(int[] neighbors) {
        for (int neighbor : neighbors) {
            if (neighbor == WATER) {
                return WATER;
            }
        }
        return EMPTY;
    }
}
