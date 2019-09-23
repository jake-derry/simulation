package game.simulation;

import game.Cell;

public class PercolationSimulation extends Simulation {
    private static final int WALL = 1;
    private static final int WATER = 2;

    PercolationSimulation(String title, Cell[][] initialGrid, int windowSize) {
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
