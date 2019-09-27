package game.Simulation;

import game.Simulation.Cell.Cell;
import static game.Simulation.State.EMPTY;
import static game.Simulation.State.WATER;

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

    /**
     * Initializes a percolation simulation
     */
    public PercolationSimulation(String title, Cell[][] initialGrid) {
        super(title, initialGrid);
    }

    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                getCell(i, j).setNextState(percolate(i, j));
            }
        }
    }

    private State percolate(int i, int j) {
        State state = getCell(i, j).getState();
        if (state != EMPTY) {
            return state;
        }
        return wetNeighbor(getEightNeighborStates(i, j));
    }

    private State wetNeighbor(State[] neighbors) {
        for (State neighbor : neighbors) {
            if (neighbor == WATER) {
                return WATER;
            }
        }
        return EMPTY;
    }
}
