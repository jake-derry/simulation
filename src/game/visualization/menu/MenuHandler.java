package game.visualization.menu;

import game.Simulation.Simulation;
import game.visualization.menu.buttons.LoadNewSimButton;
import game.visualization.menu.buttons.PausePlayButton;
import game.visualization.menu.buttons.SpeedControlButton;
import game.visualization.menu.buttons.StepButton;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class MenuHandler {

    /**
     * addTitleTextToDisplayGroup: Adds the Title text to the display group
    */
    public static void addTitleTextToDisplayGroup(Group group, int windowSize, String title){
        int TITLE_X = windowSize/16;
        int TITLE_Y = windowSize*1/10;
        int TITLE_SIZE = windowSize/16;
        Text titleText = new Text(TITLE_X, TITLE_Y, title);
        titleText.setFont(new Font(TITLE_SIZE));
        group.getChildren().add(titleText);
    }

    /**
     * Creates and returns the specific menu buttons to be displayed and used
     * @return- an ArrayList<Button> to be added to the display group in CAApp
     * Assumptions- the functions of these buttons will be set in CAApp before adding them to the display group
     */
    public static void addMenuButtonsToDisplayGroup(Stage stage, Group group, Simulation sim, int windowSize, String language){
        ResourceBundle myResources = ResourceBundle.getBundle(language);
        int NEW_SIM_X = windowSize/32;
        int PAUSE_PLAY_X = windowSize*7/32;
        int STEP_X = windowSize*13/32;
        int FASTER_X = windowSize*25/32;
        int SLOWER_X = windowSize*19/32;
        int BUTTON_Y = windowSize*3/20;
        int BUTTON_HEIGHT = windowSize/30;
        LoadNewSimButton newSimButton = new LoadNewSimButton(NEW_SIM_X, BUTTON_Y, BUTTON_HEIGHT, myResources, stage, group, sim, windowSize, language);
        group.getChildren().add(newSimButton.getButton());
        PausePlayButton pausePlayButton = new PausePlayButton(PAUSE_PLAY_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim);
        group.getChildren().add(pausePlayButton.getButton());
        StepButton stepButton = new StepButton(STEP_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim);
        group.getChildren().add(stepButton.getButton());
        SpeedControlButton fasterButton = new SpeedControlButton(FASTER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim, 0.5);
        group.getChildren().add(fasterButton.getButton());
        SpeedControlButton slowerButton = new SpeedControlButton(SLOWER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim, 2.0);
        group.getChildren().add(slowerButton.getButton());
    }

}