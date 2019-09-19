package game;

import game.simulation.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main JavaFX application. Creates and calls Configurer, Simulation, and Visualization classes.
 */
public class CAApp extends Application {
    private static final int WINDOW_SIZE = 600;
    private static final int FRAMES_PER_SECOND = 10;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Stage myStage;
    private Simulation mySim;
    private Group displayGroup;

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
        6. call createMenu to create menu items
        7. add rectangles from simulation and menu items from createMenu to display group
         */

        this.myStage = stage;
        displayGroup = new Group();
        stage.setScene(new Scene(displayGroup, WINDOW_SIZE, WINDOW_SIZE, Color.AZURE));
        stage.setTitle("Test Title");
        stage.show();

        mySim = Configurer.getSimulation("GameofLife_Example.xml", WINDOW_SIZE);
        addAssetsToDisplayGroup();

        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

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
        mySim.step();
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

    /**
     * addAssetsToDisplayGroup: adds menu items and cell rectangles to the group to be displayed
     */
    public void addAssetsToDisplayGroup(){
        for (Cell cellRow[] : mySim.getGrid()){
            for (Cell cell : cellRow){
                displayGroup.getChildren().add(cell.getRectangle());
            }
        }
        /*
        for (Node menuItem : sim.getMenuItems()){
            displayGroup.getChildren().add(menuItem);
        }*/

    }
}

