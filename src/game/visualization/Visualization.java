package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Iterator;
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
    private Simulation mySim;
    private Iterator<Rectangle> rectangleIterator;
    
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
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowDimension, language);
        MenuHandler.addTitleTextToDisplayGroup(group, windowDimension, sim.getSimTitle());
        rectangleIterator = GridHandler.setUpRectangles(windowDimension, sim.getGridRowCount(), sim.getGridColumnCount(), myGroup);
    }

    public void visualize(){
        GridHandler.visualizeCells(rectangleIterator, mySim, colorList);
    }
}
