package game.simulation;


import game.Cell;

/**
 * This simulation runs Conway's Game of Life which
 * has rules that simulate life.
 */
public class GameOfLifeSimulation extends Simulation {
    private static final int DEAD = 0;
    private static final int LIVE = 1;

    public GameOfLifeSimulation(String title, Cell[][] initialGrid, int windowSize) {
        super(title, initialGrid, windowSize);
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
        int[] neighborStates = getNeighborStates(i, j);
        int count = countLiveNeighbors(neighborStates);
        int state = getCell(i, j).getState();
        if (state == DEAD) {
            if (count == 3) {
                return LIVE;
            }
            else {
                return DEAD;
            }
        }

        else {
            if (count < 2 || count > 3) {
                return DEAD;
            }
            else {
                return LIVE;
            }
        }
    }

    private int countLiveNeighbors(int[] neighborStates) {
        int count = 0;
        for (int neighborState : neighborStates) {
            if (neighborState == LIVE) count++;
        }
        return count;
    }
}
