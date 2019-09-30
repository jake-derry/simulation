package game.visualization;

import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
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
    private final int DEFAULT_WINDOW_SIZE = 200;
    private final int DEFAULT_MILLI_DELAY = 5000;
    private Group myGroup;
    private Simulation mySim;
    private LineChart cellGraph;
    private Object colorMap;
    private int millisecondDelay;
    private List rectangleList;
    private List seriesList;
    private int windowHeight;

    public Visualization(Group group, Simulation sim, Stage stage, String language, Timeline animation, Map stylingMap){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        setDelay(stylingMap);
        setWindowHeight(stylingMap);
        cellGraph = GraphHandler.setUpStateGraph(group, windowHeight, language);
        //TODO: Dummy delay (should read from styling)
        seriesList = new ArrayList<XYChart.Series>();
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, millisecondDelay, animation, language, cellGraph, seriesList);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        rectangleList = GridHandler.setUpPolygons(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup, stylingMap, 6);
        colorMap = stylingMap.get("colorMap");
    }

    public void visualize(){
        GridHandler.visualizeCells(rectangleList.iterator(), mySim.getGrid().iterator(), (Map)colorMap);
        GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid().iterator(), mySim.getStepCount());
    }

    public int getDelay(){
        return millisecondDelay;
    }

    private void setWindowHeight(Map stylingMap){
        if (stylingMap.containsKey("windowDimension")){
            windowHeight = (int) stylingMap.get("windowDimension");
        }
        else{
            windowHeight = DEFAULT_WINDOW_SIZE;
        }
    }

    private void setDelay(Map stylingMap){
        if (stylingMap.containsKey("delay")){
            millisecondDelay = (int) stylingMap.get("delay");
        }
        else{
            millisecondDelay = DEFAULT_MILLI_DELAY;
        }
    }

    public int getWindowHeight(){
        return windowHeight;
    }

    public int getWindowWidth(){
        return windowHeight*2;
    }
}
