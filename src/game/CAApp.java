package game;

import game.simulation.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/**
 * Main JavaFX application. Calls Configurer to read in XML file and create simulation of proper type, Simulation to calculate and step through
 * states of cells based on the rules, and DisplayHandler to create and position menu assets to be displayed.
 * Assumptions:
 * Dependencies: Display Handler, Configuration, Simulation
 * Use case: this is the top level of the CA simulation. It handles what exactly is being displayed in the window and represents the piece the
 * user can interact with and see
 * @author Matt Harris
 */
public class CAApp extends Application {
    private static final int WINDOW_SIZE = 600;
    private static final Color BACKGROUND_COLOR = Color.LIGHTGRAY;
    private static int FRAMES_PER_SECOND = 5;
    private static int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Group displayGroup;
    private Timeline myAnimation;
    private Simulation mySim;
    private DisplayHandler myDisplayHandler;

    private Button myNewSimButton;
    private Button myPauseResumeButton;
    private Button myStepButton;
    private Button myFasterButton;
    private Button mySlowerButton;

    private boolean simRunning;
    private Stage myStage;

    /**
     * start: Starts the simulation and animation of the app. Here the display group, display handler, simulation, and animation are all defined and started.
     * @param stage- stage to display application on
     */
    @Override
    public void start(Stage stage){
        myStage = stage;
        displayGroup = new Group();
        myAnimation = new Timeline();
        myDisplayHandler = new DisplayHandler(WINDOW_SIZE);

        mySim = Configurer.getSimulation("Segregation.xml", WINDOW_SIZE);
        myStage.setScene(new Scene(displayGroup, WINDOW_SIZE, WINDOW_SIZE, BACKGROUND_COLOR));
        myStage.setTitle(mySim.getSimTitle());
        myStage.show();
        startApp();
    }

    /**
     * startApp: Sets up the display group (with the help of the display handler) and starts the animation timeline
     */
    private void startApp(){
        simRunning = true;
        setUpDisplayGroup();
        startAnimation();
    }

    /**
     * setUpDisplayGroup: Refreshes display group and adds Title, Menu Items, and Cells to be displayed
     */
    private void setUpDisplayGroup(){
        displayGroup.getChildren().clear();
        addTitleTextToDisplayGroup();
        addMenuButtonsToDisplayGroup();
        addCellsToDisplayGroup();
    }

    /**
     * addTitleTextToDisplayGroup: Adds the Title text to the display group
     */
    private void addTitleTextToDisplayGroup(){
        displayGroup.getChildren().add(myDisplayHandler.createTitle(mySim.getSimTitle()));
    }

    /**
     * addMenuButtonsToDisplayGroup: Adds the Menu Buttons to display group
     */
    private void addMenuButtonsToDisplayGroup(){
        ArrayList<Button> menuButtons = myDisplayHandler.makeMenuButtons();
        myNewSimButton = menuButtons.get(0);
        myPauseResumeButton = menuButtons.get(1);
        myStepButton = menuButtons.get(2);
        mySlowerButton = menuButtons.get(3);
        myFasterButton = menuButtons.get(4);
        myNewSimButton.setOnAction(setNewSimHandler());
        myPauseResumeButton.setOnAction(setPauseResumeHandler());
        myStepButton.setOnAction(setStepHandler());
        mySlowerButton.setOnAction(setSpeedHandler(2));
        myFasterButton.setOnAction(setSpeedHandler(0.5));
        displayGroup.getChildren().addAll(menuButtons);
    }

    /**
     * setNewSimHandler: Sets the specfic button action for the New Sim Button
     * @return- event handler for New Sim Button
     */
    private EventHandler<ActionEvent> setNewSimHandler(){
        final FileChooser fileChooser = new FileChooser();
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                    mySim = Configurer.getSimulation(file.getName(), WINDOW_SIZE);
                    startApp();
                }
            }
        };
        return handler;
    }

    /**
     * setPauseResumeHandler: Sets the specfic button action for the Pause/Resume Button
     * @return- event handler for Pause/Resume Button
     */
    private EventHandler<ActionEvent> setPauseResumeHandler(){
        EventHandler<ActionEvent> handler = e -> {
            if (simRunning) {
                mySim.pause();
                myPauseResumeButton.setText("Play");
                simRunning = false;
            } else {
                mySim.play();
                myPauseResumeButton.setText("Pause");
                simRunning = true;
            }
        };
        return handler;
    }

    /**
     * setStepHandler: Sets the specfic button action for the Step Button
     * @return- event handler for Step Button
     */
    private EventHandler<ActionEvent> setStepHandler(){
        EventHandler<ActionEvent> handler = e -> {
            if (!simRunning){
                mySim.play();
                mySim.step();
                mySim.pause();
            }
        };
        return handler;
    }

    /**
     * setSpeedHandler: Sets the specfic button action for the Simulation Speed Buttons
     * @param multiplier- applying a multiplier > 1 to the MILLISECOND_DELAY decreases the framerate of the animation
     *                  while applying a multiplier < 1 increases the framerate of the animation
     * @return- event handler for Sim Speed Buttons
     * Note: a check had to added in order to keep the speed from being increased to a point of crashing the app
     */
    private EventHandler<ActionEvent> setSpeedHandler(double multiplier){
        EventHandler<ActionEvent> handler = e -> {
            if (MILLISECOND_DELAY > 50 || multiplier > 1){
                MILLISECOND_DELAY = (int) (multiplier*MILLISECOND_DELAY);
                startAnimation();
            }
        };
        return handler;
    }

    /**
     * addCellsToDisplayGroup: adds cell rectangles to the group to be displayed
     */
    private void addCellsToDisplayGroup(){
        for (Cell cellRow[] : mySim.getGrid()){
            for (Cell cell : cellRow){
                displayGroup.getChildren().add(cell.getRectangle());
            }
        }
    }

    /**
     * startAnimation: stops the previously running animation and creates a new Timeline based on a perhaps updated MILLISECOND_DELAY
     */
    private void startAnimation(){
        myAnimation.pause();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    /**
     * step: calls the simulation to step through calculating and updating states according to the pace of the current timeline
     */
    private void step(){
        mySim.step();
    }
    
    public static void main (String[] args) {
        launch(args);
    }
}

