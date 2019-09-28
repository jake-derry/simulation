package game.Simulation.Cell;

import game.Simulation.State;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
public class Cell {
    private State myState;
    private State myNextState;
    private Rectangle myRectangle;

    private int row;
    private int column;

    /**
     * Constructor for Cell. Initializes the state of the
     * cell.
     *
     * @param state         Initial state of the Cell
     */
    public Cell(State state) {
        myState = state;
        myRectangle = new Rectangle();
    }

    /**
     * Constructor for Cell. Initializes the state of the
     * cell, and stores row and column information.
     *
     * @param state         Initial state of the Cell
     * @param cellRow       Which row in the grid this cell is
     * @param cellColumn    Which column in the grid this cell is
     */
    public Cell(State state, int cellRow, int cellColumn) {
        this(state);
        row = cellRow;
        column = cellColumn;
    }

    /**
     * Sets the next state to the parameter next. To change
     * the state, the setNextState method should be called
     * and then the {link #stepState(Color[] palette) stepState}
     * method which sets the state to the next state.
     *
     * @param next  sets the next state
     */
    public void setNextState(State next) {
        myNextState = next;
    }

    /**
     * Sets the current state to the next state, stepping the state
     * forward. Once, the state has been updated, the method sets
     * the color of the rectangle to match the palette taken in as
     * a parameter. This palette is expressed as an array of colors
     * where the state i's color is at index i.
     *
     * @param palette   array of colors of the same length
     *                  as the number of states for a
     *                  simulation
     */
    public void stepState(Color[] palette) {
        myState = myNextState;
        //myRectangle.setFill(palette[myState]);
    }

    /**
     * Gets the cell's rectangle object. The Visualization class will
     * leverage this method to retrieve the cell's objects and display
     * them.
     *
     * @return      the cell's rectangle
     */
    public Rectangle getRectangle() {
        return myRectangle;
    }

    /**
     * Gets the cell's state. The Simulation will use this method
     * to retrieve the cell's state to determine the state of neighboring
     * cells.
     *
     * @return      the cell's state
     */
    public State getState() {
        return myState;
    }

    /**
     * Gets the row of the cell
     *
     * @return      the cell's row in the grid
     */
    public int getRow() {
        return row;
    }

    /**
     * Gets the column of the cell
     *
     * @return      the cell's column in the grid
     */
    public int getColumn() {
        return column;
    }

}