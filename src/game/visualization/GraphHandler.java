package game.visualization;

import game.Simulation.Cell.Utils.CellUtils;
import game.Simulation.CellGrid;
import game.Simulation.State;
import javafx.scene.Group;
import javafx.scene.chart.*;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Matt Harris
 * This is a static class to control the graph of cell states over time.
 * Dependencies: CellGrid
 */
public class GraphHandler {

    /**
     * This method creates the LineChart used to plot the state of cells over time.
     * Assumptions: the LineChart will be created and displayed in the same window and displayGroup as the Menu and CellGrid
     * Assumptions: the positioning of the LineChart is calculated based off of WindowSize, not controlled by a styling xml
     * @param group- the group the linechart will be added to and displayed in
     * @param windowHeight- the Height of the display window, used to calculate the Chart positioning
     * @param language- used to determine what text to display on the Chart
     * @return- the created LineChart to be maintained in Visualization
     */
    public static LineChart setUpStateGraph(Group group, int windowHeight, String language){
        ResourceBundle resources = ResourceBundle.getBundle(language);
        NumberAxis stepAxis = new NumberAxis();
        NumberAxis cellAxis = new NumberAxis();
        stepAxis.setLabel(resources.getString("ChartXAxis"));
        cellAxis.setLabel(resources.getString("ChartYAxis"));
        LineChart<Number, Number> stateGraph = new LineChart<>(stepAxis, cellAxis);
        stateGraph.setTitle(resources.getString("ChartTitle"));
        stateGraph.setPrefHeight(windowHeight*3/4);
        stateGraph.setPrefWidth(windowHeight*3/4);
        stateGraph.setLayoutX(windowHeight*9/8);
        stateGraph.setLayoutY(windowHeight*1/8);
        stateGraph.setAnimated(false);
        stateGraph.setCreateSymbols(false);
        group.getChildren().add(stateGraph);
        return stateGraph;
    }

    /**
     * This method updates the graph based on the new Cell states found by iterating with the CellGrid
     * Assumptions: the LineChart will not be cleared over the course of a simulation
     * @param stateGraph- the LineChart to be updated
     * @param seriesList- the List of DataSeries to be updated and displayed in the LineChart
     * @param cellIterator- allows us to iterate through all cells and update LineChart based on their updated states
     * @param stepCount- the current step count of the simulation, used for determining x axis positioning of data
     */
    public static void updateGraph(LineChart stateGraph, List<XYChart.Series> seriesList, CellGrid cellIterator, int stepCount){
        Map<State, Integer> stateMap = CellUtils.countMap(cellIterator);
        for (State key:stateMap.keySet()){
            boolean newSeries = true;
            for (XYChart.Series series : seriesList){
                if (key.toString().equals(series.getName())){
                    series.getData().add(new XYChart.Data(stepCount, stateMap.get(key)));
                    newSeries = false;
                }
            }
            if (newSeries){
                XYChart.Series newDataSeries = new XYChart.Series();
                newDataSeries.setName(key.toString());
                newDataSeries.getData().add(new XYChart.Data(stepCount, stateMap.get(key)));
                seriesList.add(newDataSeries);
            }
        }
        stateGraph.getData().clear();
        stateGraph.getData().addAll(seriesList);
    }
}

