package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.CellUtils;
import game.Simulation.State;
import javafx.scene.Group;
import javafx.scene.chart.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphHandler {
    public static LineChart setUpStateGraph(Group group, int windowHeight){
        NumberAxis stepAxis = new NumberAxis();
        NumberAxis cellAxis = new NumberAxis();
        stepAxis.setLabel("Sim Step Number");
        cellAxis.setLabel("Number of Cells");
        LineChart<Number, Number> stateGraph = new LineChart<>(stepAxis, cellAxis);
        stateGraph.setTitle("State of Cells Over Time");
        stateGraph.setPrefHeight(windowHeight*3/4);
        stateGraph.setPrefWidth(windowHeight*3/4);
        stateGraph.setLayoutX(windowHeight*9/8);
        stateGraph.setLayoutY(windowHeight*1/8);
        stateGraph.setAnimated(false);
        group.getChildren().add(stateGraph);
        return stateGraph;
    }

    public static void updateGraph(LineChart stateGraph, List<XYChart.Series> seriesList, Iterator<Cell> cellIterator, int stepCount){
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

