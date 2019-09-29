package game.Simulation.Cell;

import game.Simulation.Neighborhood;
import game.Simulation.State;

import java.util.*;

/**
 * This class represents a single cell in a cellular
 * automata simulation that is stored in a grid. It
 * holds information the cells current and next state
 * to prevent incorrect updating of cells.
 *
 * The cell's state cannot be changed directly. Instead,
 * the state is updated as the next state on a method call.
 *
 * @author Jake Derry
 */
abstract public class Cell {

    private State myState;
    private State myNextState;
    private Iterable<Cell> myNeighborhood;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state         Initial state of the Cell
     */
    public Cell(State state) {
        myState = state;
        myNextState = state;

    }

    public void setNeighbors(Iterable<Cell> neighborhood) {
        myNeighborhood = neighborhood;
    }

    /**
     * Performs cellular automata logic to determine
     * the next state of the cell. Sets the next
     * state of the cell using setNextState.
     */
    abstract public void updateNext();

    /**
     * Sets the current state to the next state, stepping the state
     * forward.
     */
    public void stepState() {
        myState = myNextState;
    }

    /**
     * Gets the cell's state. The Simulation will use this method
     * to retrieve the cell's state to determine the state of neighboring
     * cells.
     *
     * @return          the cell's state
     */
    public State getState() {
        return myState;
    }

    /**
     * Gets the cell's neighbors. This is only used by subclasses to
     * implement updateNext.
     *
     * @return          the cell's neighbors in an Iterator
     */
    protected Iterable<Cell> getNeighbors() {
        return myNeighborhood;
    }

    /**
     * Sets the current state of the simulation using setNextState and
     * stepState.
     *
     * @param state     the cell's new state
     */
    public void setState(State state) {
        myState = state;
    }

    /**
     * Sets the next state based on updateNext. To change
     * the state, the setNextState method should be called
     * and then the method which sets the state to the next
     * state. Updates the count map that keeps track of the
     * number of neighbors of each state.
     *
     */
    public void setNextState(State next) {
        myNextState = next;
    }

    /**
     *
     * @return
     */
    protected State getNextState() {
        return myNextState;
    }

    /**
     * Gets a map of the counts of each state among the
     * cell's neighbors.
     *
     * @return      map of state counts of neighbors
     */
    protected Map<State, Integer> getCountMap() {
        return CellUtils.countMap(getNeighbors());
    }

}