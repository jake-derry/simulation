package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import game.visualization.GraphHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;

import java.util.List;
import java.util.ResourceBundle;

public class StepButton extends MenuButton {

    private Simulation mySim;
    private LineChart cellGraph;
    private List seriesList;
    public StepButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim, LineChart graph, List series){
        super(xPos, yPos, height, resources);
        mySim = sim;
        myButton.setText(resources.getString("Step"));
        cellGraph = graph;
        seriesList = series;
        setButtonAction();
    }

    @Override
    public void setButtonAction() {
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                if (!mySim.getSimRunning()){
                    mySim.play();
                    mySim.step();
                    mySim.pause();
                    GraphHandler.updateGraph(cellGraph, seriesList, mySim.getGrid(), mySim.getStepCount());
                }
            }
        });
    }
}
