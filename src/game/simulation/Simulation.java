package game.simulation;

import game.Cell;

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

    Simulation() { }

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
    public void stop() {

    }

    /**
     * Sets the running variable to true which resumes
     * the {link #update update} method to run.
     */
    public void start() {

    }

    /**
     * Gets the grid of Cells of the Simulation.
     *
     * @return  grid of Cells
     */
    public Cell[][] getGrid() {
        return null;
    }
}