package game;

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
    private int myState;
    private int myNextState;
    private Rectangle myRectangle;

    /**
     * Sets the next state to the parameter next. To change
     * the state, the setNextState method should be called
     * and then the {link #stepState(Color[] palette) stepState}
     * method which sets the state to the next state.
     *
     * @param next  sets the next state
     */
    public void setNextState(int next) {

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

    }

    /**
     * Gets the cell's rectangle object. The Visualization class will
     * leverage this method to retrieve the cell's objects and display
     * them.
     *
     * @return      the cell's rectangle
     */
    public Rectangle getRectangle() {
        return null;
    }

}