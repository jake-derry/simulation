package game.simulation;

import game.Cell;
import util.ArrayUtils;

import java.util.List;
import java.util.Random;

public class PredatorPreySimulation extends Simulation {
    private static final boolean WRAP_AROUND = true;
    private static final int PREDATOR = 1;
    private static final int PREY = 2;

    private static final int NO_ENERGY = -1;

    private int myBreedTime;
    private int clock;

    private int[][] predatorEnergies;
    private int initialEnergy;
    private int energyThreshold;


    public PredatorPreySimulation(String title, Cell[][] initialGrid, int windowSize,
                                  int breedTime, int predatorInitialEnergy, int predatorEnergyThreshold) {
        super(title, initialGrid, windowSize);
        myBreedTime = breedTime;
        clock = 0;
        predatorEnergies = setupPredatorEnergies(initialGrid);
        initialEnergy = predatorInitialEnergy;
        energyThreshold = predatorEnergyThreshold;
    }

    private int[][] setupPredatorEnergies(Cell[][] initialGrid) {
        int[][] energies = new int[initialGrid.length][initialGrid[0].length];
        for (int i = 0; i < initialGrid.length; i++) {
            for (int j = 0; j < initialGrid[0].length; j++) {
                if (initialGrid[i][j].getState() == PREDATOR) {
                    energies[i][j] = initialEnergy;
                }
                else {
                    energies[i][j] = NO_ENERGY;
                }
            }
        }
        return energies;
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
        int state = getCell(i, j, WRAP_AROUND).getState();
        if (state == PREDATOR) getCell(i, j, WRAP_AROUND).setNextState(hunt(i, j));
        else if (state == PREY) getCell(i, j, WRAP_AROUND).setNextState(run(i, j));
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
            getCell(i, j, WRAP_AROUND).setNextState(EMPTY);

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
        Random random = new Random();

        int emptyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), EMPTY);
        int preyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), PREY);
        int availableCellCount = emptyCount + preyCount;

        if (availableCellCount > 0) {
            int randomIndex = random.nextInt(availableCellCount);

            List<Cell> neighbors = getEightNeighbors(i, j);
            Cell newPredatorLocation = neighbors.get(
                    findOccurrence(neighbors, randomIndex)
            );
            neighbors.remove(newPredatorLocation);

            if (predatorEnergies[i][j] > energyThreshold) {
                int randomChildIndex = random.nextInt(availableCellCount - 1);

                Cell newPredatorChildLocation = neighbors.get(
                        findOccurrence(neighbors, randomChildIndex)
                );

                movePredator(newPredatorChildLocation);
            }

            movePredator(newPredatorLocation);

            getCell(i, j, WRAP_AROUND).setNextState(EMPTY);
            predatorEnergies[i][j] = NO_ENERGY;
        }
        return PREDATOR;
    }

    private void movePredator(Cell newPredatorChildLocation) {
        newPredatorChildLocation.setNextState(PREDATOR);
        predatorEnergies[newPredatorChildLocation.getRow()][newPredatorChildLocation.getColumn()] +=
                energyChange(newPredatorChildLocation.getState());
    }

    private int energyChange(int state) {
        if (state == EMPTY) return -1;
        return 1;
    }
}