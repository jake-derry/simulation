package game.Simulation;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.Utils.CellUtils;
import util.Pair;

import java.util.*;

/**
 * An Iterable of cells that denotes a neighborhood of
 * cells
 */
public class Neighborhood implements Iterable<Cell> {
    List<Cell> myNeighbors;
    Map<Pair<Integer, Integer>, Cell> myNeighborhoodMap;
    Random random;

    /**
     * Initializes a neighborhood based on a map of displacement
     * coordinates to cells
     * @param neighbors         Map of displacement coordinates to cells
     */
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

    /**
     * Gets cell below
     *
     * @return      Cell below
     */
    public Cell getDown() {
        return myNeighborhoodMap.get(new Pair<Integer, Integer>(0, -1));
    }

    /**
     * Gets cell above
     *
     * @return      Cell above
     */
    public Cell getRight() {
        return myNeighborhoodMap.get(new Pair<Integer, Integer>(-1, 0));
    }

    /**
     * Gets cell left
     *
     * @return      Cell left
     */
    public Cell getLeft() {
        return myNeighborhoodMap.get(new Pair<Integer, Integer>(1, 0));
    }

    /**
     * Gets cell right
     *
     * @return      Cell right
     */
    public Cell getUp() {
        return myNeighborhoodMap.get(new Pair<Integer, Integer>(0, 1));
    }
}
