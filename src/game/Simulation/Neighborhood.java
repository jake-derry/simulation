package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Iterator;

public class Neighborhood implements Iterable<Cell> {


    @Override
    public Iterator<Cell> iterator() {
        return CellIterator();
    }
}
