package game.simulation;

import game.Cell;
import util.ArrayUtils;

import java.util.List;
import java.util.Random;

public class PredatorPreySimulation extends Simulation {
    private static final int PREDATOR = 1;
    private static final int PREY = 2;

    private int myBreedTime;
    private int clock;

    PredatorPreySimulation(String title, Cell[][] initialGrid, int windowSize, int breedTime) {
        super(title, initialGrid, windowSize);
        myBreedTime = breedTime;
        clock = 0;
    }

    @Override
    protected void update() {
        for (int i = 0; i < getGridRowCount(); i++) {
            for (int j = 0; j < getGridColumnCount(); j++) {
                nextState(i, j);
            }
        }
    }

    private void nextState(int i, int j) {
        int state = getCell(i, j).getState();
        if (state == PREDATOR) getCell(i, j).setNextState(hunt(i, j));
        else if (state == PREY) getCell(i, j).setNextState(run(i, j));
    }

    private int run(int i, int j) {
        Random random = new Random();
        int emptyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), EMPTY);
        if (emptyCount > 0) {
            int randomEmptyIndex = random.nextInt(emptyCount);

            List<Cell> neighbors = getEightNeighbors(i, j);
            Cell newPreyLocation = neighbors.get(
                    findOccurrence(neighbors, randomEmptyIndex)
            );
            neighbors.remove(newPreyLocation);

            newPreyLocation.setNextState(PREY);
            getCell(i, j).setNextState(EMPTY);

            if (clock == myBreedTime && emptyCount > 1) {
                int randomEmptyChildIndex = random.nextInt(emptyCount - 1);

                Cell newPreyChildLocation = neighbors.get(
                        findOccurrence(neighbors, randomEmptyChildIndex)
                );

                newPreyChildLocation.setNextState(PREY);
                clock = 0;
            }
            else {
                clock++;
            }
        }
        return PREY;
    }

    private int findOccurrence(List<Cell> neighbors, int index) {
        int count = 0;
        for (int x = 0; x < neighbors.size(); x++) {
            if (neighbors.get(x).getState() == EMPTY) {
                if (count < index) {
                    count++;
                }
                else {
                    return x;
                }
            }
        }
        return -1;
    }

    private int hunt(int i, int j) {
        int emptyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), EMPTY);
        if (emptyCount > 0) {

        }
        return PREDATOR;
    }
}
