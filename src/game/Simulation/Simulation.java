package game.Simulation;

import game.Simulation.Cell.Cell;
import game.visualization.Visualization;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private Map<String, Object> parameterMap;
    private int delay;
    private Visualization myVisualization;
    private Timeline myAnimation;
    private String simTitle;

    /**
     * Initializes a simulation running.
     * @param parameterMap  A map of the parameter name and
     *                      the parameter object
     * @param initialGrid   Initial grid of the simulation
     */
    public Simulation(Map<String, Object> parameterMap, String[][] initialGrid) {
        running = true;
        grid = new CellGrid(parameterMap, initialGrid);
        delay = (int) parameterMap.get("millisecondDelay");
        simTitle = parameterMap.get("title").toString();
        myAnimation = new Timeline();
        startAnimation();
    }

    public void setVisualization(Group group, Stage stage, int windowSize, String language){
        myVisualization = new Visualization(group, this, stage, windowSize, language);
    }

    public Timeline getAnimation(){
        return myAnimation;
    }

    public void startAnimation(){
        myAnimation.pause();
        myAnimation = new Timeline();
        var frame = new KeyFrame(Duration.millis(delay), e -> step());
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
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

    public int getDelay(){
        return delay;
    }

    /**
     * Gets the simTitle for a sim
     */
    public String getSimTitle() {
        return parameterMap.get("Title").toString();
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