package game.Simulation.Cell;

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
    private int myState;
    private int myNextState;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state         Initial state of the Cell
     */
    public Cell(int state) {
        myState = state;
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
    public int getState() {
        return myState;
    }

    /**
     * Sets the current state of the simulation using setNextState and
     * stepState.
     *
     * TODO: Possibly block changes in the cell state between the call of
     * TODO: setNextState and stepState
     *
     * @param state     the cell's new state
     */
    public void setState(int state) {
        setNextState(state);
        stepState();
    }

    /**
     * Sets the next state to the parameter next. To change
     * the state, the setNextState method should be called
     * and then the method which sets the state to the next
     * state.
     *
     * @param next  sets the next state
     */
    public void setNextState(int next) {
        myNextState = next;
    }

}