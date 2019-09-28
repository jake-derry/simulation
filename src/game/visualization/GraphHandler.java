package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.CellUtils;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.Iterator;
import java.util.Map;

public class GraphHandler {
    public static LineChart setUpStateGraph(Group group, int windowHeight){
        NumberAxis stepAxis = new NumberAxis();
        NumberAxis cellAxis = new NumberAxis();
        stepAxis.setLabel("Sim Step Number");
        cellAxis.setLabel("Number of Cells");
        LineChart<Number, Number> stateGraph = new LineChart<Number, Number>(stepAxis, cellAxis);
        stateGraph.setTitle("State of Cells Over Time");
        stateGraph.setPrefHeight(windowHeight*3/4);
        stateGraph.setPrefWidth(windowHeight*3/4);
        stateGraph.setLayoutX(windowHeight*9/8);
        stateGraph.setLayoutY(windowHeight*1/8);
        group.getChildren().add(stateGraph);
        return stateGraph;
    }

    public static void updateGraph(LineChart stateGraph, Iterator<Cell> cellIterator){
        Map<Integer, Integer> stateMap = CellUtils.countMap(cellIterator);
        for (Integer key:stateMap.keySet()){
            XYChart.Series series = new XYChart.Series();
            series.getData().add(new XYChart.Data(key, stateMap.get(key)));
            stateGraph.getData().add(series);
        }
    }
}
