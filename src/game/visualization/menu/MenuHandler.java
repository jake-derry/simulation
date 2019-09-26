package game.visualization.menu;

import game.Simulation.Simulation;
import game.visualization.menu.buttons.LoadNewSimButton;
import game.visualization.menu.buttons.PausePlayButton;
import game.visualization.menu.buttons.SpeedControlButton;
import game.visualization.menu.buttons.StepButton;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ResourceBundle;

public class MenuHandler {
    private Group myGroup;
    private Simulation mySim;
    private Stage myStage;
    private Timeline myAnimation;
    private String myLanguage;
    private final int MENU_HEIGHT;
    private final int WINDOW_SIZE;
    // Title Text
    private final int TITLE_X;
    private final int TITLE_Y;
    private final int TITLE_SIZE;
    // Button Positioning
    private final int BUTTON_HEIGHT;
    private final int NEW_SIM_X;
    private final int PAUSE_PLAY_X;
    private final int STEP_X;
    private final int BUTTON_Y;
    private final int FASTER_X;
    private final int SLOWER_X;

    public MenuHandler(Group group, Simulation sim, int size, Stage stage, Timeline animation, String language){
        myGroup = group;
        mySim = sim;
        myStage = stage;
        myAnimation = animation;
        myLanguage = language;
        WINDOW_SIZE = size;
        MENU_HEIGHT = WINDOW_SIZE/4;
        TITLE_X = WINDOW_SIZE/16;
        TITLE_Y = MENU_HEIGHT*2/5;
        TITLE_SIZE = MENU_HEIGHT/4;
        NEW_SIM_X = WINDOW_SIZE/32;
        PAUSE_PLAY_X = WINDOW_SIZE*7/32;
        STEP_X = WINDOW_SIZE*13/32;
        FASTER_X = WINDOW_SIZE*25/32;
        SLOWER_X = WINDOW_SIZE*19/32;
        BUTTON_Y = MENU_HEIGHT*3/5;
        BUTTON_HEIGHT = WINDOW_SIZE/30;
        setUpMenu();
    }

    private void setUpMenu(){
        addTitleTextToDisplayGroup();
        addMenuButtonsToDisplayGroup();
    }

    /**
     * addTitleTextToDisplayGroup: Adds the Title text to the display group
    */
    private void addTitleTextToDisplayGroup(){
        myGroup.getChildren().add(createTitle(mySim.getSimTitle()));
    }

    /**
     * Creates the tile of the displayed simulation at a particular size and particular position
     * @param text- the String that is to be displayed as Text in the window
     * @return- the Text object created to display the Title
     * Assumptions: position and size of title specified by private variables TITLE_X, TITLE_Y, and TITLE_SIZE
     */
    private Text createTitle(String text){
        Text finalText = new Text(TITLE_X, TITLE_Y, text);
        finalText.setFont(new Font(TITLE_SIZE));
        return finalText;
    }

    /**
     * Creates and returns the specific menu buttons to be displayed and used
     * @return- an ArrayList<Button> to be added to the display group in CAApp
     * Assumptions- the functions of these buttons will be set in CAApp before adding them to the display group
     */
    private void  addMenuButtonsToDisplayGroup(){
        ResourceBundle myResources = ResourceBundle.getBundle(myLanguage);
        LoadNewSimButton newSimButton = new LoadNewSimButton(NEW_SIM_X, BUTTON_Y, BUTTON_HEIGHT, myResources, myStage, myGroup, mySim, WINDOW_SIZE, myLanguage);
        myGroup.getChildren().add(newSimButton.getButton());
        PausePlayButton pausePlayButton = new PausePlayButton(PAUSE_PLAY_X, BUTTON_Y, BUTTON_HEIGHT, myResources, mySim);
        myGroup.getChildren().add(pausePlayButton.getButton());
        StepButton stepButton = new StepButton(STEP_X, BUTTON_Y, BUTTON_HEIGHT, myResources, mySim);
        myGroup.getChildren().add(stepButton.getButton());
        SpeedControlButton fasterButton = new SpeedControlButton(FASTER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, mySim, 0.5);
        myGroup.getChildren().add(fasterButton.getButton());
        SpeedControlButton slowerButton = new SpeedControlButton(SLOWER_X, BUTTON_Y, BUTTON_HEIGHT, myResources, mySim, 2.0);
        myGroup.getChildren().add(slowerButton.getButton());
    }

}