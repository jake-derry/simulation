package game.simulation;

import game.Cell;
import game.Visualization;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
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
    private static final int BEYOND_EDGE = -1;

    private Cell[][] grid;
    private boolean running;
    private Visualization myVisualization;
    private String simTitle;

    public Simulation(String title, Cell[][] initialGrid, int windowSize){
        running = true;
        simTitle = title;
        grid = initialGrid;
        myVisualization = new Visualization(initialGrid, new HashMap<>(), windowSize);
    }

    /**
     * Calls the update() method when the running
     * variable is set to true.
     */
    public void step() {
        if (running) update();
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
     * @param x     x index of grid
     * @param y     y index of grid
     * @return      Cell at x, y
     */
    protected Cell getCell(int x, int y) {
        try {
            return grid[x][y];
        }
        catch (IndexOutOfBoundsException e) {
            return new Cell(BEYOND_EDGE);
        }
    }

    protected int[] getNeighborStates(int i, int j) {
        int[] neighborStates = new int[8];
        neighborStates[0] = getCell(i - 1, j).getState();
        neighborStates[1] = getCell(i + 1, j).getState();
        neighborStates[2] = getCell(i - 1, j - 1).getState();
        neighborStates[3] = getCell(i + 1, j - 1).getState();
        neighborStates[4] = getCell(i, j - 1).getState();
        neighborStates[5] = getCell(i - 1, j + 1).getState();
        neighborStates[6] = getCell(i + 1, j + 1).getState();
        neighborStates[7] = getCell(i, j + 1).getState();
        return neighborStates;
    }

}