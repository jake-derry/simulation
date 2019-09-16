package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    private int myState;
    private int myNextState;
    private Rectangle myRectangle;

    /**
     * Setter for myNextState
     * @param next  new value of myNextState
     */
    void setNextState(int next) {

    }

    /**
     * Sets myState to the value of
     * myNextState and sets the color
     * of the cell rectangle to the
     * palette color that matches the
     * state
     * @param palette   array of colors of the same length
     *                  as the number of states for a
     *                  simulation
     */
    void stepState(Color[] palette) {

    }

    /**
     * Getter for myRectangle
     * @return      myRectangle
     */
    Rectangle getRectangle() {
        return null;
    }

}