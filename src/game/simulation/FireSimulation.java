package game.simulation;

import game.Cell;

import java.util.Random;

public class FireSimulation extends Simulation {
    private static final int EMPTY = 0;
    private static final int BURNING = 1;
    private static final int TREE = 2;

    /**
     * Probability of a tree cell catching fire
     */
    double myProbCatch;

    public FireSimulation (String title, Cell[][] initialGrid, int windowSize, double probCatch) {
        super(title, initialGrid, windowSize);
        myProbCatch = probCatch;
    }


    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                getCell(i, j).setNextState(spread(i, j));
            }
        }
    }

    private int spread(int i, int j) {
        int[] neighbors = getFourNeighborStates(i, j);
        int state = getCell(i, j).getState();
        if (state == TREE) {
            int burningNeighbors = burning(neighbors);
            return catchState(burningNeighbors);
        }
        return EMPTY;
    }

    private int catchState(int neighbors) {
        Random random = new Random();
        for (int i = 0; i < neighbors; i++) {
            if (random.nextFloat() < myProbCatch) {
                return BURNING;
            }
        }
        return TREE;
    }

    private int burning(int[] neighbors) {
        int count = 0;
        for (int neighbor : neighbors) {
            if (neighbor == BURNING) count++;
        }
        return count;
    }
}
