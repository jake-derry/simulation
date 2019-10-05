package game.Simulation.Cell;

import game.Simulation.Cell.Utils.CellUtils;
import game.Simulation.Neighborhood;
import game.Simulation.State;

import java.util.*;

/**
 * This class represents a single cell in a cellular
 * automata simulation that is stored in a grid. It
 * holds information about the cells current and next
 * state to prevent incorrect updating of cells.
 *
 * DEPENDENCIES:
 *      Neighborhood
 *
 * @author Jake Derry
 */
abstract public class Cell {

    private State myState;
    private State myNextState;
    private Neighborhood myNeighborhood;
    private static List<State> myStateList;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell, the next state of the cell, and a list of available
     * states to that cell.
     *
     * @param state         Initial state of the Cell
     */
    public Cell(State state) {
        myState = state;
        myNextState = state;
        myStateList = new ArrayList<>();
    }

    /**
     * Performs cellular automata logic to determine
     * the next state of the cell. Sets the next
     * state of the cell using setNextState.
     */
    abstract public void updateNext();

    /**
     * Sets the current state based on the next state.
     */
    public void stepState() {
        myState = myNextState;
    }

    /**
     * Connects cells to one another by setting its Neighborhood
     * which is an Iterable of Cells.
     *
     * @see Neighborhood
     * @param neighborhood      A Neighborhood object that contains
     *                          the Cell's neighbors
     */
    public void setNeighborhood(Neighborhood neighborhood) {
        myNeighborhood = neighborhood;
    }

    /**
     * Sets the current state of the simulation.
     *
     * @param state     the cell's new state
     */
    public void setState(State state) {
        myState = state;
        myNextState = state;
    }

    /**
     * Sets the next state.
     *
     * @param next      the cell's next state
     */
    void setNextState(State next) {
        myNextState = next;
    }

    public void setStateList(List<State> stateList){
        myStateList = stateList;
    }

    /**
     * Gets the cell's neighbors.
     *
     * @see Neighborhood
     * @return          the cell's neighborhood in a Neighborhood object
     */
    protected Neighborhood getNeighborhood() {
        return myNeighborhood;
    }

    /**
     * Gets the cell's state.
     *
     * @return          the cell's state
     */
    public State getState() {
        return myState;
    }

    /**
     * Gets the next state.
     *
     * @return          the cell's next state
     */
    State getNextState() {
        return myNextState;
    }

    /**
     * Gets a map of the counts of each state among the
     * cell's neighbors.
     *
     * @return      Map of state counts of neighbors
     */
    protected Map<State, Integer> getCountMap() {
        return CellUtils.countMap(getNeighborhood());
    }

    /**
     * Gets a map of the cells of each state among the
     * cell's neighbors.
     *
     * @return          Map with state keys and a list of
     *                  cells as the value
     */
    protected Map<State, List<Cell>> getCellMap() {
        return CellUtils.cellMap(getNeighborhood());
    }

    /**
     * Gets the next state in the stateList. Calling this function
     * allows the visualization to retrieve the states of a simulation
     * type in a way that makes it accessible on click.
     *
     * @return          The next state in the stateList
     */
    public State getNextStateOnClick(){
        if (myStateList.indexOf(myState) + 1 == myStateList.size()){
            return myStateList.get(0);
        }
        return myStateList.get(myStateList.indexOf(myState) + 1);
    }

}