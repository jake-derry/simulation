package game.simulation;

import game.Cell;
import game.Visualization;

import java.util.Map;

/**
 * This abstract class runs a simulation of any
 * type. This type changes how the simulation is
 * updated. The simulation contains an array of cells
 * that are the basis of the cellular automata simulation.
 *
 * This simulation constantly updates if it is running.
 *
 * @author Jake Derry
 */
abstract public class Simulation {
    private Cell[][] grid;
    private boolean running;
    private Visualization myVisualization;


    Simulation() {
        running = true;
    }

    /**
     * Calls the update() method when the running
     * variable is set to true.
     */
    public void step() {
        if (running) update();
    }

    /**
     * Sets the next state of all cells in the
     * grid. Only runs when the running variable is set
     * to true.
     */
    protected abstract void update();

    /**
     * Sets the running variable to false which stops
     * the {link #update update} method from running.
     */
    public void pause() {
        running = false;
    }

    /**
     * Sets the running variable to true which resumes
     * the {link #update update} method to run.
     */
    public void play() {
        running = true;
    }

    /**
     * Gets the grid of Cells of the Simulation.
     *
     * @return  grid of Cells
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Gets the row count of the grid in cell.s
     *
     * @return      grid height
     */
    public int getGridRowCount() {
        return grid.length;
    }

    /**
     * Gets the column count of the grid in cells.
     *
     * Assumes that the grid has at least 1 row.
     *
     * @return      grid width
     */
    public int getGridColumnCount() {
        return grid[0].length;
    }

    /**
     * Gets the visualization of the simulation.
     *
     * @return  Visualization object of the simulation
     */
    public Visualization getVisualization() {
        return myVisualization;
    }

    /**
     * Gets the cell at x, y in the 2D array (grid) of cells.
     * @param x     x index of grid
     * @param y     y index of grid
     * @return      Cell at x, y
     */
    protected Cell getCell(int x, int y) {
        return grid[x][y];
    }
}