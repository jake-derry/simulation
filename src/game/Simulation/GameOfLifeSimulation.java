package game.Simulation;


import game.Simulation.Cell.Cell;

import static game.Simulation.State.EMPTY;
import static game.Simulation.State.LIVE;

/**
 * This simulation runs Conway's Game of Life which
 * has rules that simulate life. Only able to run a
 * Conway's Game of Life simulation and assumes that
 * the user does not want the cell grid to 'wrap around.'
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Simulation
 * @author Jake Derry
 */
public class GameOfLifeSimulation extends Simulation {

    /**
     * Initializes a game of life simulation
     */
    public GameOfLifeSimulation(String title, Cell[][] initialGrid) {
        super(title, initialGrid);
    }

    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                getCell(i, j).setNextState(nextState(i, j));
            }
        }
    }

    private State nextState(int i, int j) {
        State[] neighborStates = getEightNeighborStates(i, j);
        int count = countLiveNeighbors(neighborStates);
        State state = getCell(i, j).getState();
        if (state == EMPTY) {
            if (count == 3) {
                return LIVE;
            }
            else {
                return EMPTY;
            }
        }

        else {
            if (count < 2 || count > 3) {
                return EMPTY;
            }
            else {
                return LIVE;
            }
        }
    }

    private int countLiveNeighbors(State[] neighborStates) {
        int count = 0;
        for (State neighborState : neighborStates) {
            if (neighborState == LIVE) count++;
        }
        return count;
    }
}
