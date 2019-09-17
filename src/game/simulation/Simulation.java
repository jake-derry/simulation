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
     * Sets the next state of all cells in the grid
     * implemented based on type of simulation. Only
     * does this when the running variable is set to true.
     */
    public abstract void update();

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
     * Gets the cell at x, y in the 2D array (grid) of cells.
     * @param x     x index of grid
     * @param y     y index of grid
     * @return      Cell at x, y
     */
    public Cell getCell(int x, int y) {
        return grid[x][y];
    }

    /**
     * Gets the visualization of the simulation.
     *
     * @return  Visualization object of the simulation
     */
    public Visualization getVisualization() {
        return myVisualization;
    }
}