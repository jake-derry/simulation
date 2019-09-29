package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.CellUtils;
import game.Simulation.CellGrid;
import game.Simulation.Simulation;
import game.Simulation.State;
import game.visualization.menu.MenuHandler;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.*;

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

    private Group myGroup;
    private Simulation mySim;
    private Iterator<Rectangle> rectangleIterator;
    private LineChart cellGraph;
    private HashMap<State,Color> colorMap;
    private int stepCount;
    private int millisecondDelay;
    private List rectangleList;
    private List seriesList;

    public Visualization(Group group, Simulation sim, Stage stage, int windowHeight, String language, Timeline animation){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        WINDOW_HEIGHT = windowHeight;
        cellGraph = GraphHandler.setUpStateGraph(group, windowHeight);
        //TODO: Dummy delay (should read from styling)
        millisecondDelay = 500;
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, millisecondDelay, animation, language);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        rectangleList = GridHandler.setUpRectangles(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup);

        //TODO: DUMMY COLOR MAP
        colorMap= new HashMap<>();
        colorMap.put(State.EMPTY, Color.BLACK);
        colorMap.put(State.BURNING, Color.RED);
        colorMap.put(State.TREE, Color.GREEN);

        stepCount = 0;
        seriesList = new ArrayList<XYChart.Series>();
    }

    public void visualize(){
        if (mySim.getSimRunning()){
            Map<State, Integer> x = CellUtils.countMap(mySim.getGrid().iterator());
            Iterator<Cell> cellIterator = mySim.getGrid().iterator();
            GridHandler.visualizeCells(rectangleList.iterator(), cellIterator, colorMap);
            GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid().iterator(), stepCount);
            stepCount++;
        }
    }

    public void setMillisecondDelay(int delay){
        millisecondDelay = delay;
    }

    public int getDelay(){
        return millisecondDelay;
    }
}
