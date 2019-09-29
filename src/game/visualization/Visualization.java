package game.visualization;

import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
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
    private int WINDOW_HEIGHT;
    private int MENU_HEIGHT;
    private Color[] colorList;

    private Group myGroup;
    private Simulation mySim;
    private Iterator<Rectangle> rectangleIterator;
    private LineChart cellGraph;

    public Visualization(Group group, Simulation sim, Stage stage, int windowHeight, String language){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        WINDOW_HEIGHT = windowHeight;
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.RED;
        colorList[2] = Color.YELLOW;
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, language);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        rectangleIterator = GridHandler.setUpRectangles(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup);

    }

    public void visualize(){
        GridHandler.visualizeCells(rectangleIterator, mySim, colorList);

        GraphHandler.updateGraph(cellGraph, mySim.getGrid().iterator());
    }
}
