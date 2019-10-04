package game.visualization;

import game.Simulation.CellShape;
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
 * @author Matt Harris
 * This class is responsible for maintaining and controlling the different assets to be displayed in the app including the menu, grid of cells, and graph of cell states
 * In addition, visualization calls the simulation to step forward and update the states of cells based on the rules of the simulation
 * Assumptions: the simulation will not need to be independently called to update independent of updating the visualization
 * Dependencies: Simulation, MenuHandler, GraphHandler, GridHandler, CellShape
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

    /**
     * The constructor for a visualization object
     * @param group- the displayGroup to be maintained by the Visualization
     * @param sim- the simulation linked to be displayed and called to update
     * @param stage- the stage the MenuButtons will control
     * @param language- used to determine what text to display in the app
     * @param animation- the Timeline to be controlled by the MenuButtons
     * @param stylingMap- contains StylingParameters used to setup the display and created by Configurer reading a styling xml
     */
    public Visualization(Group group, Simulation sim, Stage stage, String language, Timeline animation, Map stylingMap){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        setDelay(stylingMap);
        setWindowHeight(stylingMap);
        setBackgroundColor(stylingMap);
        cellGraph = GraphHandler.setUpStateGraph(group, windowHeight, language);
        seriesList = new ArrayList<XYChart.Series>();
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, animation, language, cellGraph, seriesList);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        int polygonSides = CellShape.matchShape((String) mySim.getParameterMap().get("shape")).getSides();
        polygonList = GridHandler.setUpPolygons(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup, stylingMap, polygonSides, mySim);
        colorMap = (Map) stylingMap.get("colorMap");
        myBackGroundColor = Paint.valueOf(colorMap.get("backgroundColor").toString());
    }

    /**
     * Calls the Visualization to update and the Simulation to step forward
     * Assumptions: the Simulation will not need to be called to step forward independently of the visualization updating
     */
    public void step(){
        visualize();
        mySim.step();
    }

    /**
     * Getter for the delay of the Timeline (set from a styling xml) maintained by Visualization, helpful in creating new Timelines for newly created visualizations
     * @return- the delay for the visualization from the styling xml
     */
    public int getDelay(){
        return millisecondDelay;
    }

    /**
     * Getter for the windowHeight of the visualization set up in styling xml
     * @return- windowHeight of visualization
     */
    public int getWindowHeight(){
        return windowHeight;
    }

    /**
     * Getter for the windowWidth of the visualization set up in styling xml
     * Assumptions: windowWidth will always be twice windowHeight to allow for room for graph of cell states
     * @return- the windowWidth of the visualization
     */
    public int getWindowWidth(){
        return windowHeight*2;
    }

    /**
     * Getter for the background color of the visualization set from styling xml
     * @return- background color of visualization
     */
    public Paint getBackgroundColor(){
        return myBackGroundColor;
    }

    private void visualize(){
        GridHandler.visualizeCells(polygonList.iterator(), mySim.getGrid().iterator(), colorMap);
        GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid(), mySim.getStepCount());
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
        if (stylingMap.containsKey("backgroundColor")){
            myBackGroundColor = (Color) stylingMap.get("backgroundColor");
        }
        else{
            myBackGroundColor = DEFAULT_BACKGROUND_COLOR;
        }
    }
}
