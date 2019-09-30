package game.Simulation;

import game.Simulation.Cell.Cell;

import java.util.Map;

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
    private Map<String, Object> myParameterMap;
    private int stepCount;

    /**
     * Initializes a simulation running.
     * @param parameterMap  A map of the parameter name and
     *                      the parameter object
     * @param initialGrid   Initial grid of the simulation
     */
    public Simulation(Map<String, Object> parameterMap, String[][] initialGrid) {
        myParameterMap = parameterMap;
        running = false;
        grid = new CellGrid(parameterMap, initialGrid);
        stepCount = 0;
    }

    /**
     * Calls the update() method when the running
     * variable is set to true.
     */
    public void step() {
        if (running){
            update();
            stepCount++;
        }
    }

    public int getStepCount(){
        return stepCount;
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

    public boolean getSimRunning(){
        return running;
    }

    /**
     * Gets the simTitle for a sim
     */
    public String getSimTitle() {
        return myParameterMap.get("Simulation").toString();
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
        int x = 0;
        for (Cell cell : grid) {
            cell.updateNext();
        }
        for (Cell cell : grid) {
            cell.stepState();
        }
    }

    /**
     * Gets parameter map of the simulation
     *
     * @return          A map of the parameters of a simulation
     */
    public Map<String, Object> getParameterMap(){
        return myParameterMap;
    }
}