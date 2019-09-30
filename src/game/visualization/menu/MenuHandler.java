package game.visualization.menu;

import game.Simulation.Simulation;
import game.visualization.menu.buttons.LoadNewSimButton;
import game.visualization.menu.buttons.PausePlayButton;
import game.visualization.menu.buttons.SpeedControlButton;
import game.visualization.menu.buttons.StepButton;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Matt Harris
 * This is a static class to handle all of the assets to be displayed in the Menu including MenuButtons and TitleText
 * Assumptions: the items in the menu will be only the MenuButtons and TitleText unless this file is changed
 * Assumptions: positioning of all items is defaulted and not controlled by a user or styling xml
 * Dependencies: MenuButton, LoadNewSimButton, PausePlayButton, SpeedControlButton, StepButton
 */
public class MenuHandler {

    /**
     * This method takes in a group and adds TitleText to that group to be displayed at the top of running app, based on the size of the Window
     * Assumptions: the positioning and size is calculated based on windowSize not controlled by styling xml
     * @param group- the group to add the TitleText to
     * @param windowSize- the size of the app window, used for calculating positioning and size of font
     * @param title- the actual words to be displayed as the title
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
     * This method adds the implemented MenuButtons to control the visualization and simulation to the passed displayGroup
     * @param stage- the stage these buttons will live in and control
     * @param group- the group these buttons will be added to
     * @param sim- the simulation these buttons will control
     * @param windowSize- the windowSize of the scene these buttons are a part of (used for positioning)
     * @param animation- the Timeline these buttons will control
     * @param language- used to determine the text to be displayed on the buttons
     * @param cellGraph- the LineChart to be controlled by the buttons (only StepButton)
     * @param seriesList- the List of dataSeries to be controlled by buttons and displayed in the cellGraph
     */
    public static void addMenuButtonsToDisplayGroup(Stage stage, Group group, Simulation sim, int windowSize, Timeline animation, String language, LineChart cellGraph, List seriesList){
        ResourceBundle myResources = ResourceBundle.getBundle(language);
        int NEW_SIM_X = windowSize/32;
        int PAUSE_PLAY_X = windowSize*7/32;
        int STEP_X = windowSize*13/32;
        int SLOWER_X = windowSize*25/32;
        int FASTER_X = windowSize*19/32;
        int BUTTON_Y = windowSize*3/20;
        int BUTTON_HEIGHT = windowSize/30;
        LoadNewSimButton newSimButton = new LoadNewSimButton(NEW_SIM_X, BUTTON_Y, BUTTON_HEIGHT, myResources, stage, language);
        group.getChildren().add(newSimButton.getButton());
        PausePlayButton pausePlayButton = new PausePlayButton(PAUSE_PLAY_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim);
        group.getChildren().add(pausePlayButton.getButton());
        StepButton stepButton = new StepButton(STEP_X, BUTTON_Y, BUTTON_HEIGHT, myResources, sim, cellGraph, seriesList);
        group.getChildren().add(stepButton.getButton());
        SpeedControlButton fasterButton = new SpeedControlButton(FASTER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, animation,0.5);
        group.getChildren().add(fasterButton.getButton());
        SpeedControlButton slowerButton = new SpeedControlButton(SLOWER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, animation,2.0);
        group.getChildren().add(slowerButton.getButton());
    }

}