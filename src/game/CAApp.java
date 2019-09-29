package game;

import game.Configurer.Configurer;
import game.Simulation.Simulation;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
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
    private static final int WINDOW_HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = Color.LIGHTGRAY;
    private String language = "English";

    private Group displayGroup;
    private static Simulation mySim;

    private Stage myStage;

    /**
     * start: Starts the simulation and animation of the app. Here the display group, display handler, simulation, and animation are all defined and started.
     * @param stage- stage to display application on
     */
    @Override
    public void start(Stage stage){
        myStage = stage;
        displayGroup = new Group();
        Map<String, Object> myMap = Configurer.getStyling("FireStyle.xml");
        mySim = Configurer.getSimulation("Fire.xml");
        mySim.setVisualization(displayGroup, myStage, WINDOW_HEIGHT, language);
        myStage.setScene(new Scene(displayGroup, WINDOW_HEIGHT, WINDOW_HEIGHT, BACKGROUND_COLOR));
        myStage.setTitle(mySim.getSimTitle());
        myStage.show();
    }

    public static void setSim(Simulation sim){
        mySim = sim;
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

