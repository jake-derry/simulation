package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;
import java.util.List;

public class Neighborhood implements Iterable<Cell> {
    List<Cell> myNeighbors;

    public Neighborhood(List<Cell> neighbors) {
        myNeighbors = neighbors;
    }


    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(myNeighbors);
    }

    public Cell chooseNeighbor(State[] acceptableStates) {
        return null;
    }
}
