package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;

/**
 * Visualization: created by a simulation in order to display the states in the cell grid.
 * Assumptions: at max, we will have 3 different unique states to be displayed for cells, represented by colorList
 * Dependencies: Cell
 * Use Case: a visualization is made for a simulation's gridOfCells when it is created and this will track and display the grid in the window
 * @author Matt Harris
 */
public class Visualization{
    private String DEFAULT_RESOURCE_PACKAGE = "/data/";
    private int WINDOW_SIZE;
    private int MENU_HEIGHT;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;
    private Color[] colorList;
    private ResourceBundle myResources;
    private Timeline myAnimation;
    private Group myGroup;
    private MenuHandler myMenuHandler;
    private GridHandler myGridHandler;
    private Simulation mySim;
    private int myDelay;
    private boolean simRunning;
    
    public Visualization(Timeline animation, Group group, Simulation sim, Stage stage, int windowDimension, String language){
        myAnimation = animation;
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        WINDOW_SIZE = windowDimension;
        MENU_HEIGHT = WINDOW_SIZE/4;
        CELL_WIDTH = WINDOW_SIZE / mySim.getGridColumnCount();
        CELL_HEIGHT = (WINDOW_SIZE - MENU_HEIGHT) / mySim.getGridRowCount();
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.RED;
        colorList[2] = Color.YELLOW;
        myResources = ResourceBundle.getBundle(language);
        simRunning = true;
        startAnimation();
        myMenuHandler = new MenuHandler(myGroup, mySim, WINDOW_SIZE, stage, myAnimation, language);
        myGridHandler = new GridHandler(mySim, myGroup, WINDOW_SIZE);
    }

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public void visualize(){
        for (Cell[] cellRow : mySim.getGrid()){
            for (Cell cell : cellRow){
                cell.stepState(colorList);
            }
        }
    }



    /**
     * startApp: Sets up the display group (with the help of the display handler) and starts the animation timeline
     */
    public void startApp(){
        simRunning = true;
        setUpDisplayGroup();
        startAnimation();
    }

    /**
     * setUpDisplayGroup: Refreshes display group and adds Title, Menu Items, and Cells to be displayed
     */
    public void setUpDisplayGroup(){
        myGroup.getChildren().clear();
    }

    /**
     * startAnimation: stops the previously running animation and creates a new Timeline based on a perhaps updated MILLISECOND_DELAY
     */
    private void startAnimation(){
        myAnimation.pause();
        var frame = new KeyFrame(Duration.millis(mySim.getMILLISECOND_DELAY()), e -> mySim.step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

}
