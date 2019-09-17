package game.simulation;

import game.Cell;


/**
 * This simulation runs Conway's Game of Life which
 * has rules that simulate life.
 */
public class GameOfLifeSimulation extends Simulation {
    int DEAD = 0;
    int LIVE = 1;

    @Override
    public void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); i++) {
                updateCell(i, j);
            }
        }
    }

    private void updateCell(int i, int j) {
        int[] neighborStates = getNeighborStates(i, j);
    }

    private int[] getNeighborStates(int i, int j) {
        int[] neighborStates = new int[4];
        neighborStates[0] = getCell(i-1, j).getState();
        neighborStates[1] = getCell(i+1, j).getState();
        neighborStates[2] = getCell(i, j-1).getState();
        neighborStates[3] = getCell(i, j+1).getState();
        return neighborStates;
    }
}
