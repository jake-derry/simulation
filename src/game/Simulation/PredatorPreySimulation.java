package game.Simulation;

import game.Simulation.Cell.Cell;
import util.ArrayUtils;
import java.util.List;
import java.util.Random;

import static game.Simulation.State.EMPTY;
import static game.Simulation.State.PREDATOR;
import static game.Simulation.State.PREY;

/**
 * This simulation runs a Predator-Prey Simulation which
 * has rules that simulate a small food chain. Only able
 * to run a Predator-Prey (Wator) simulation and assumes
 * that the user wants the cell grid to 'wrap around.'
 *
 * DEPENDENCIES:
 *      Cell
 *
 * @see Simulation
 * @author Jake Derry
 */
public class PredatorPreySimulation extends Simulation {
    private static final boolean WRAP_AROUND = true;

    private static final int NO_ENERGY = -1;

    private int myBreedTime;
    private int clock;

    private int[][] predatorEnergies;

    private int initialEnergy;
    private int energyThreshold;

    /**
     * Initializes a predator-prey simulation
     *
     * @param breedTime                 Number of updates before the prey 'reproduce'
     * @param predatorInitialEnergy     Initial energy that each predator has
     * @param predatorEnergyThreshold   Energy level at which the predator 'reproduces'
     */
    public PredatorPreySimulation(String title, Cell[][] initialGrid,
                                  int breedTime, int predatorInitialEnergy, int predatorEnergyThreshold) {
        super(title, initialGrid);
        myBreedTime = breedTime;
        clock = 0;
        initialEnergy = predatorInitialEnergy;
        predatorEnergies = setupPredatorEnergies(initialGrid);
        energyThreshold = predatorEnergyThreshold;
    }

    private int[][] setupPredatorEnergies(Cell[][] initialGrid) {
        int[][] energies = new int[initialGrid.length][initialGrid[0].length];
        for (int i = 0; i < initialGrid.length; i++) {
            for (int j = 0; j < initialGrid[0].length; j++) {
                if (initialGrid[i][j].getState() == PREDATOR) {
                    System.out.println(initialEnergy);
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
                System.out.printf("(%d, %d) : %d", i, j, predatorEnergies[i][j]);
                nextState(i, j);
            }
        }
        System.out.println();
    }

    private void nextState(int i, int j) {
        State state = getCell(i, j, WRAP_AROUND).getState();
        if (state == PREY) getCell(i, j, WRAP_AROUND).setNextState(run(i, j));
        else if (state == PREDATOR) getCell(i, j, WRAP_AROUND).setNextState(hunt(i, j));
    }

    private State run(int i, int j) {
        Random random = new Random();
        int emptyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), EMPTY);
        if (emptyCount > 0) {
            int randomEmptyIndex = random.nextInt(emptyCount);

            List<Cell> neighbors = getEightNeighbors(i, j);
            State[] acceptableStates = {EMPTY};
            Cell newPreyLocation = neighbors.get(
                    findOccurrence(neighbors, randomEmptyIndex, acceptableStates)
            );
            neighbors.remove(newPreyLocation);

            newPreyLocation.setNextState(PREY);
            getCell(i, j, WRAP_AROUND).setNextState(EMPTY);

            if (clock == myBreedTime && emptyCount > 1) {
                int randomEmptyChildIndex = random.nextInt(emptyCount - 1);

                Cell newPreyChildLocation = neighbors.get(
                        findOccurrence(neighbors, randomEmptyChildIndex, acceptableStates)
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

    private int findOccurrence(List<Cell> neighbors, int index, State[] acceptableStates) {
        int count = 0;
        for (int x = 0; x < neighbors.size(); x++) {
            for (State state : acceptableStates) {
                if (neighbors.get(x).getState() == state) {
                    if (count < index) {
                        count++;
                    }
                    else {
                        return x;
                    }
                }
            }
        }
        return -1;
    }

    private State hunt(int i, int j) {
        Random random = new Random();

        int emptyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), EMPTY);
        int preyCount = ArrayUtils.countIf(getEightNeighborStates(i, j), PREY);
        int availableCellCount = emptyCount + preyCount;

        if (predatorEnergies[i][j] <= 0) {
            getCell(i, j).setNextState(EMPTY);
            predatorEnergies[i][j] = NO_ENERGY;
            return EMPTY;
        }

        else if (availableCellCount > 0) {
            int randomIndex = random.nextInt(availableCellCount);

            List<Cell> neighbors = getEightNeighbors(i, j);
            int[] acceptableStates = {EMPTY, PREY};
            Cell newPredatorLocation = neighbors.get(
                    findOccurrence(neighbors, randomIndex, acceptableStates)
            );
            neighbors.remove(newPredatorLocation);

            if (availableCellCount > 1 && predatorEnergies[i][j] > energyThreshold) {
                int randomChildIndex = random.nextInt(availableCellCount - 1);

                Cell newPredatorChildLocation = neighbors.get(
                        findOccurrence(neighbors, randomChildIndex, acceptableStates)
                );

                movePredator(i, j, newPredatorChildLocation);
            }

            movePredator(i, j, newPredatorLocation);

            getCell(i, j, WRAP_AROUND).setNextState(EMPTY);
        }
        return PREDATOR;
    }

    private void movePredator(int i, int j, Cell newPredatorChildLocation) {
        newPredatorChildLocation.setNextState(PREDATOR);
        predatorEnergies[newPredatorChildLocation.getRow()][newPredatorChildLocation.getColumn()] = predatorEnergies[i][j] +
                energyChange(newPredatorChildLocation.getState());
    }

    private int energyChange(State state) {
        if (state == EMPTY) return -1;
        return 1;
    }
}