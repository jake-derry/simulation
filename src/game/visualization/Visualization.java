package game.visualization;

import game.Simulation.Simulation;
import game.Simulation.State;
import game.visualization.menu.MenuHandler;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
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
    private Group myGroup;
    private Simulation mySim;
    private LineChart cellGraph;
    private Object colorMap;
    private int millisecondDelay;
    private List rectangleList;
    private List seriesList;

    public Visualization(Group group, Simulation sim, Stage stage, int windowHeight, String language, Timeline animation, Map stylingMap){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        cellGraph = GraphHandler.setUpStateGraph(group, windowHeight);
        //TODO: Dummy delay (should read from styling)
        millisecondDelay = 500;
        seriesList = new ArrayList<XYChart.Series>();
        MenuHandler.addMenuButtonsToDisplayGroup(stage, group, sim, windowHeight, millisecondDelay, animation, language, cellGraph, seriesList);
        MenuHandler.addTitleTextToDisplayGroup(group, windowHeight, sim.getSimTitle());
        rectangleList = GridHandler.setUpRectangles(windowHeight, sim.getGrid().getCellRows(), sim.getGrid().getCellColumns(), myGroup);

        //TODO: DUMMY COLOR MAP
        colorMap = stylingMap.get("colorMap");
        int x = 0;
    }

    public void visualize(){
        GridHandler.visualizeCells(rectangleList.iterator(), mySim.getGrid().iterator(), (Map)colorMap);
        GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid().iterator(), mySim.getStepCount());
    }

    public int getDelay(){
        return millisecondDelay;
    }
}
