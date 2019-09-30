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

    /**
     * @author Matt Harris
     * This class implements MenuButton and represents a button capable of controlling the simulation and graph to step once ahead
     * Assumptions: a "step" will always represent a single update to the graph and simulation (cannot step any number of steps forward),
     * Assumption: "stepping" the simulation will only be allowed while it is paused
     * Dependencies: Simulation
     * @param xPos- xPosition in the scene for the button to appear
     * @param yPos- yPosition in the scene for the button to appear
     * @param height- height of the button to be created
     * @param resources- the ResourcesBundle to pull the text from
     * @param sim- simulation being controlled by the stepButton
     * @param graph- LineChart being controlled by the stepButton
     * @param series- the data to be passed to the LineChart to be displayed
     */
    public StepButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim, LineChart graph, List series){
        super(xPos, yPos, height, resources);
        mySim = sim;
        myButton.setText(resources.getString("Step"));
        cellGraph = graph;
        seriesList = series;
        setButtonAction();
    }

    @Override
    protected void setButtonAction() {
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
