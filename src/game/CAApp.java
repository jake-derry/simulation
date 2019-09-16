package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Main JavaFX application. Creates and calls Configurer, Simulation, and Visualization classes.
 */
public class CAApp extends Application {
    private Stage myStage;
    private Simulation mySim;

    /**
     *
     * @param stage Stage to display application on
     */
    @Override
    public void start(Stage stage){
        /* Basic Flow Idea
        1. setup configuration object
        2. call configuration.readXML (gathers data from initialization file)
        3. call configuration.createGrid (takes gathered info and creates a "grid" of Cells with proper grid dimensions, initial states, but no rectangle info)
        4. call configuration.createSimulation (takes type information and created grid to make a new simulation)
        5. call visualization.setUpRectangles when simulation is created (takes grid dimensions and fills in rectangle info)
         */

        this.myStage = stage;
    }

    /**
     *
     * @param elapsedTime corresponds to the second delay to take
     */
    private void step(double elapsedTime){
        /* Basic Flow Idea
        1. call simulation.Update
        2. call simulation.visualize
         */
    }

    public static void main (String[] args) {
        launch(args);
    }

    /**
     *
     * @param xPos x coordinate of screen click
     * @param yPos y coordinate of screen click
     */
    private void handleMouseInput(double xPos, double yPos){

    }

    /**
     *
     * @param code KeyCode of key pressed by user
     */
    private void handleKeyInput(KeyCode code){

    }
}

