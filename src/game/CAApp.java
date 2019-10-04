package game;

import game.Configurer.Configurer;
import game.Simulation.Simulation;
import game.visualization.Visualization;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Harris
 * CAApp: Main JavaFX application. Maintains a list of active simulations and visualizations and starts the app by calling configurer to read the default
 * simulatiion file to create the first simulation, calling configurer to read the styling file associated with that sim to create the first visualization,
 * and setting up the first stage and timeline
 * Dependencies: Configurer, Simulation, Visualization
 */
public class CAApp extends Application {
    private String language = "English";
    private String defaultSimFile = "/simulations/MattsFireExample.xml";
    private Group displayGroup;
    private static List<Simulation> mySims;
    private static List<Visualization> myVisualizations;
    private Stage myStage;

    /**
     * Starts the app by creating the first Simulation and Visualization from the default sim file
     * @param stage- stage to display the first visualization on
     */
    @Override
    public void start(Stage stage){
        myStage = stage;
        displayGroup = new Group();
        mySims = new ArrayList<>();
        myVisualizations = new ArrayList<>();
        mySims.add(Configurer.getSimulation(defaultSimFile));
        Timeline myAnimation = new Timeline();
        Map stylingMap = Configurer.getStyling(mySims.get(0).getParameterMap().get("StylingFile").toString());
        myVisualizations.add(new Visualization(displayGroup, mySims.get(0), myStage, language, myAnimation, stylingMap));
        myStage.setScene(new Scene(displayGroup, myVisualizations.get(0).getWindowWidth(), myVisualizations.get(0).getWindowHeight(), myVisualizations.get(0).getBackgroundColor()));
        myStage.show();
        int millisecondDelay = myVisualizations.get(0).getDelay();
        var frame = new KeyFrame(Duration.millis(millisecondDelay), e -> myVisualizations.get(0).step());
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }


    /**
     *
     * @param args
     */
    public static void main (String[] args) {
        launch(args);
    }
}

