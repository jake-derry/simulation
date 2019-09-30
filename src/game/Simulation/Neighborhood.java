package game.Simulation;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.Utils.CellUtils;
import util.Pair;

import java.util.*;

/**
 * TODO: Come back to this so I can implement foraging cells
 */
public class Neighborhood implements Iterable<Cell> {
    List<Cell> myNeighbors;
    Map<Pair<Integer, Integer>, Cell> myNeighborhoodMap;
    Random random;

    public Neighborhood(Map<Pair<Integer, Integer>, Cell> neighbors) {
        myNeighbors = new ArrayList<>(neighbors.values());
        myNeighborhoodMap = neighbors;
        random = new Random();

    }

    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(myNeighbors);
    }

    public Cell chooseNeighbor(State[] acceptableStates) {
        int acceptableCount = getAcceptableCount(acceptableStates);

        int randIndex = random.nextInt(acceptableCount);
        int count = 0;
        for (Cell neighbor : myNeighbors) {
            for (State state : acceptableStates) {
                if (neighbor.getState() == state) {
                    count++;

                    if (count == randIndex) {
                        return neighbor;
                    }
                }
            }
        }
        return null;
    }

    private int getAcceptableCount(State[] acceptableStates) {
        int acceptableCount = 0;
        for (State state : acceptableStates) {
            acceptableCount += CellUtils.countMap(this).get(state);
        }
        return acceptableCount;
    }

    public Cell getDown() {
        return null;
    }

    public Cell getRight() {
        return null;
    }
}
