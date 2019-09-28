package game;

import game.Configurer.Configurer;
import game.Simulation.Simulation;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
    private static final int WINDOW_SIZE = 600;
    private static final Color BACKGROUND_COLOR = Color.LIGHTGRAY;
    private static int FRAMES_PER_SECOND = 3;
    private static int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private String language = "English";

    private Group displayGroup;
    private Timeline myAnimation;
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
        myAnimation = new Timeline();

        mySim = Configurer.getSimulation("Segregation.xml", WINDOW_SIZE, language);
        myStage.setScene(new Scene(displayGroup, WINDOW_SIZE, WINDOW_SIZE, BACKGROUND_COLOR));
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

