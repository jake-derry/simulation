package game.visualization;

import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private final Color DEFAULT_BACKGROUND_COLOR = Color.LIGHTGRAY;
    private Group myGroup;
    private Simulation mySim;
    private LineChart cellGraph;
    private Map colorMap;
    private int millisecondDelay;
    private List polygonList;
    private List seriesList;
    private int windowHeight;
    private Paint myBackGroundColor;

    public Visualization(Group group, Simulation sim, Stage stage, String language, Timeline animation, Map stylingMap){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        setDelay(stylingMap);
        setWindowHeight(stylingMap);
        setBackgroundColor(stylingMap);
        cellGraph = GraphHandler.setUpStateGraph(group, windowHeight, language);
        seriesList = new ArrayList<XYChart.Series>();
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, millisecondDelay, animation, language, cellGraph, seriesList);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        polygonList = GridHandler.setUpPolygons(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup, stylingMap, 6, mySim);
        colorMap = (Map) stylingMap.get("colorMap");
        myBackGroundColor = Paint.valueOf(colorMap.get("backgroundColor").toString());
    }
    public void step(){
        visualize();
        mySim.step();
    }

    public void visualize(){
        GridHandler.visualizeCells(polygonList.iterator(), mySim.getGrid().iterator(), (Map)colorMap);
        GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid(), mySim.getStepCount());
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

    private void setBackgroundColor(Map stylingMap){
        if (stylingMap.containsKey("bacgroundColor")){
            myBackGroundColor = (Color) stylingMap.get("backgroundColor");
        }
        else{
            myBackGroundColor = DEFAULT_BACKGROUND_COLOR;
        }
    }

    public int getWindowHeight(){
        return windowHeight;
    }

    public int getWindowWidth(){
        return windowHeight*2;
    }

    public Paint getBackgroundColor(){
        return myBackGroundColor;
    }
}
