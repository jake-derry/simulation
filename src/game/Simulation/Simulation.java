package game.Simulation;

import game.Playable;
import game.Simulation.Cell.Cell;
import game.visualization.Visualization;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

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
abstract public class Simulation implements Playable {

    private Cell[][] grid;
    private boolean running;
    private Visualization myVisualization;
    private String simTitle;
    private int MILLISECOND_DELAY;

    private List<Cell> emptyCells;

    /**
     * Initializes a simulation running.
     * @param title         Title of the simulation
     * @param initialGrid   Initial grid of the simulation
     */
    public Simulation(String title, Cell[][] initialGrid){
        running = true;
        simTitle = title;
        grid = initialGrid;
        emptyCells = findMatches(State.EMPTY);
        //TODO CHANGE THIS
        MILLISECOND_DELAY = 500;
    }

    public void setVisualization(Timeline animation, Group group, Stage stage, int windowSize, String language){
        myVisualization = new Visualization(animation, group, this, stage, windowSize, language);
    }


    /**
     * Calls the update() method when the running
     * variable is set to true.
     */
    public void step() {
        if (running){
            update();
            myVisualization.visualize();
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

    public int getMILLISECOND_DELAY(){
        return MILLISECOND_DELAY;
    }

    public void setMILLISECOND_DELAY(int delay){
        MILLISECOND_DELAY = delay;
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
     * Sets the next state of all cells in the
     * grid. Only runs when the running variable is set
     * to true.
     */
    protected abstract void update();

    /**
     * Gets the cell at x, y in the 2D array (grid) of cells. If the cell
     * does not exist, like in the case of an edge cell, a cell is returned
     * with a placeholder state.
     * @param x         x index of grid
     * @param y         y index of grid
     * @return          Cell at x, y
     */
    protected Cell getCell(int x, int y) {
        try {
            return grid[x][y];
        }
        catch (IndexOutOfBoundsException e) {
            return new Cell(State.BEYOND_EDGE);
        }
    }

    /**
     * Gets the cell at x, y in the 2D array (grid) of cells. Options of how to handle
     * out of bound indices
     * @param x         x index of grid
     * @param y         y index of grid
     * @param wrapAround    choose between the grid being (true) wrap around where
     *                  out of bound indices reach to the opposite side of the grid
     *                  or (false) surrounded by a border of cells that have
     *                  a specific state
     * @return          Cell at x, y
     */
    protected Cell getCell(int x, int y, boolean wrapAround) {
        if (wrapAround) {
            if (x > getGridRowCount()) {
                x -= getGridRowCount();
            }
            else if (x < 0) {
                x = getGridRowCount() - x;
            }

            if (y > getGridColumnCount()) {
                y -= getGridColumnCount();
            }
            else if (y < 0) {
                y = getGridRowCount() - y;
            }

            return grid[x][y];
        }
        return getCell(x, y);
    }

    protected List<Cell> getEightNeighbors(int i, int j) {
        List<Cell> neighbors= new ArrayList<>();
        neighbors.add(getCell(i - 1, j));
        neighbors.add(getCell(i + 1, j));
        neighbors.add(getCell(i - 1, j - 1));
        neighbors.add(getCell(i + 1, j - 1));
        neighbors.add(getCell(i, j - 1));
        neighbors.add(getCell(i - 1, j + 1));
        neighbors.add(getCell(i + 1, j + 1));
        neighbors.add(getCell(i, j + 1));
        return neighbors;
    }

    protected List<Cell> getFourNeighbors(int i, int j) {
        List<Cell> neighbors= new ArrayList<>();
        neighbors.add(getCell(i - 1, j));
        neighbors.add(getCell(i + 1, j));
        neighbors.add(getCell(i, j - 1));
        neighbors.add(getCell(i, j + 1));
        return neighbors;
    }

    protected State[] getEightNeighborStates(int i, int j) {
        List<Cell> neighbors = getEightNeighbors(i, j);
        State[] neighborStates = new State[neighbors.size()];
        for (int x = 0; x < neighbors.size(); x++) {
            neighborStates[x] = neighbors.get(x).getState();
        }
        return neighborStates;
    }

    protected State[] getFourNeighborStates(int i, int j) {
        List<Cell> neighbors = getFourNeighbors(i, j);
        State[] neighborStates = new State[neighbors.size()];
        for (int x = 0; x < neighbors.size(); x++) {
            neighborStates[x] = neighbors.get(x).getState();
        }
        return neighborStates;
    }

    protected List<Cell> findMatches(State state) {
        List<Cell> matches = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                Cell cell = getCell(i, j);
                if (cell.getState() == state) {
                    matches.add(cell);
                }
            }
        }
        return matches;
    }


    protected List<Cell> getEmptyCells() {
        return emptyCells;
    }

}