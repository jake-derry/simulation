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
 * Main JavaFX application. Creates and calls Configurer, Simulation, and Visualization classes.
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
     *
     * @param stage Stage to display application on
     */
    @Override
    public void start(Stage stage){
        myStage = stage;
        displayGroup = new Group();
        myAnimation = new Timeline();
        myDisplayHandler = new DisplayHandler(WINDOW_SIZE);

        mySim = Configurer.getSimulation("GameofLife_Example.xml", WINDOW_SIZE);
        myStage.setScene(new Scene(displayGroup, WINDOW_SIZE, WINDOW_SIZE, BACKGROUND_COLOR));
        myStage.setTitle(mySim.getSimTitle());
        myStage.show();
        startApp();
    }

    /**
     *
     */
    private void startApp(){
        simRunning = true;
        setUpDisplayGroup();
        startAnimation();
    }

    /**
     *
     */
    private void setUpDisplayGroup(){
        displayGroup.getChildren().clear();
        addTitleTextToDisplayGroup();
        addMenuButtonsToDisplayGroup();
        addCellsToDisplayGroup();
    }

    /**
     *
     */
    private void addTitleTextToDisplayGroup(){
        displayGroup.getChildren().add(myDisplayHandler.createText(mySim.getSimTitle()));
    }

    /**
     *
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
     *
     * @return
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
     *
     * @return
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
     *
     * @return
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
     *
     * @param multiplier
     * @return
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
    public void addCellsToDisplayGroup(){
        for (Cell cellRow[] : mySim.getGrid()){
            for (Cell cell : cellRow){
                displayGroup.getChildren().add(cell.getRectangle());
            }
        }
    }

    /**
     *
     */
    private void startAnimation(){
        myAnimation.pause();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    private void step(){
        mySim.step();
    }

    public static void main (String[] args) {
        launch(args);
    }
}

