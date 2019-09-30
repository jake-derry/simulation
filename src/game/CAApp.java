package game;

import game.Configurer.Configurer;
import game.Simulation.Simulation;
import game.visualization.Visualization;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * CAApp: Main JavaFX application. Calls Configurer to read in XML file and create simulation of proper type, Simulation to calculate and step through
 * states of cells based on the rules, and DisplayHandler to create and position menu assets to be displayed.
 * Assumptions:
 * Dependencies: Display Handler, Configuration, Simulation
 * Use case: this is the top level of the CA simulation. It handles what exactly is being displayed in the window and represents the piece the
 * user can interact with and see
 * @author Matt Harris
 */
public class CAApp extends Application {
    private static final Color BACKGROUND_COLOR = Color.LIGHTGRAY;
    private String language = "English";

    private Group displayGroup;
    private static List<Simulation> mySims;
    private static List<Visualization> myVisualizations;

    private Stage myStage;

    /**
     * start: Starts the simulation and animation of the app. Here the display group, display handler, simulation, and animation are all defined and started.
     * @param stage- stage to display application on
     */
    @Override
    public void start(Stage stage){
        myStage = stage;
        displayGroup = new Group();
        mySims = new ArrayList<>();
        myVisualizations = new ArrayList<>();
        mySims.add(Configurer.getSimulation("Fire.xml"));
        Timeline myAnimation = new Timeline();
        Map stylingMap = Configurer.getStyling(mySims.get(0).getParameterMap().get("StylingFile").toString());
        myVisualizations.add(new Visualization(displayGroup, mySims.get(0), myStage, language, myAnimation, stylingMap, 0));
        myStage.setScene(new Scene(displayGroup, myVisualizations.get(0).getWindowWidth(), myVisualizations.get(0).getWindowHeight(), BACKGROUND_COLOR));
        myStage.show();
        int millisecondDelay = myVisualizations.get(0).getDelay();
        var frame = new KeyFrame(Duration.millis(millisecondDelay), e -> myVisualizations.get(0).step());
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }

    public static void addSim(Simulation sim){
        mySims.add(sim);
    }
    public static void addVisualization(Visualization vis) { myVisualizations.add(vis); }

    public static void main (String[] args) {
        launch(args);
    }
}

