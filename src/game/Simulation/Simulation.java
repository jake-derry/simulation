package game.Simulation;

import game.Simulation.Cell.Cell;

/**
 * This abstract class runs a simulation of any
 * type. This type changes how the simulation is
 * updated. The simulation contains an array of cells
 * that are the basis of the cellular automata simulation.
 *
 * This simulation constantly updates if it is running.
 *
 * DEPENDENCIES:
 *      Visualization
 *      Cell
 *
 * USAGE:
 *      After calling the constructor, call the step method.
 *      Call the pause and play methods to determine whether
 *      the step method does action or not.
 *
 * @author Jake Derry
 */
public class Simulation {

    private CellGrid grid;
    private boolean running;
    private String simTitle;
    private int millisecondDelay;

    /**
     * Initializes a simulation running.
     * @param title         Title of the simulation
     * @param initialGrid   Initial grid of the simulation
     */
    public Simulation(String title, CellGrid initialGrid) {
        running = true;
        simTitle = title;
        grid = initialGrid;
        //TODO CHANGE THIS
        millisecondDelay = 500;
    }


    /**
     * Calls the update() method when the running
     * variable is set to true.
     */
    public void step() {
        if (running){
            update();
        }
    }

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

    public void setSimRunning(boolean bool){
        running = bool;
    }

    public boolean getSimRunning(){
        return running;
    }

    public int getMillisecondDelay(){
        return millisecondDelay;
    }

    public void setMillisecondDelay(int delay){
        millisecondDelay = delay;
    }

    /**
     * Gets the simTitle for a sim
     */
    public String getSimTitle() {
        return simTitle;
    }

    /**
     * Gets the grid of Cells of the Simulation.
     *
     * @return  CellGrid object of the Simulation
     */
    public CellGrid getGrid() {
        return grid;
    }

    /**
     * Sets the next state of all cells in the
     * grid. Only runs when the running variable is set
     * to true.
     */
    public void update() {

        for (Cell cell : grid) {
            cell.updateNext();
        }

        for (Cell cell : grid) {
            cell.stepState();
        }
    }
}