package game;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * DisplayHandler: create and position menu items (buttons, title text) for the CAApp window
 * Assumptions: there will be 5 buttons- New Sim, Pause/Resume, Step, Slower, and Faster
 * Dependencies: N/A
 * Use case: these methods can be called from CAApp to create the menu items needed to control the simulations
 * @author Matt Harris
 */
public class DisplayHandler {
    private final int MENU_HEIGHT;
    private final int WINDOW_SIZE;
    // Title Text
    private final int TITLE_X;
    private final int TITLE_Y;
    private final int TITLE_SIZE;
    // Button Positioning
    private final int BUTTON_HEIGHT;
    private final int NEW_SIM_X;
    private final int PAUSE_RESUME_X;
    private final int STEP_X;
    private final int BUTTON_Y;
    private final int FASTER_X;
    private final int SLOWER_X;

    public DisplayHandler(int windowDimension){
        WINDOW_SIZE = windowDimension;
        MENU_HEIGHT = WINDOW_SIZE/4;
        TITLE_X = WINDOW_SIZE/16;
        TITLE_Y = MENU_HEIGHT*2/5;
        TITLE_SIZE = MENU_HEIGHT/4;
        NEW_SIM_X = WINDOW_SIZE/32;
        PAUSE_RESUME_X = WINDOW_SIZE*7/32;
        STEP_X = WINDOW_SIZE*13/32;
        FASTER_X = WINDOW_SIZE*25/32;
        SLOWER_X = WINDOW_SIZE*19/32;
        BUTTON_Y = MENU_HEIGHT*3/5;
        BUTTON_HEIGHT = WINDOW_SIZE/30;
    }

    /**
     * Creates the tile of the displayed simulation at a particular size and particular position
     * @param text- the String that is to be displayed as Text in the window
     * @return- the Text object created to display the Title
     * Assumptions: position and size of title specified by private variables TITLE_X, TITLE_Y, and TITLE_SIZE
     */
    public Text createTitle(String text){
        Text finalText = new Text(TITLE_X, TITLE_Y, text);
        finalText.setFont(new Font(TITLE_SIZE));
        return finalText;
    }

    /**
     * @param text- the text to be displayed on the button
     * @param xPos- the xcoordinate of where the button should be displayed
     * @return- the button created with the specified text and position
     */
    private Button makeButton(String text, int xPos){
        Button button = new Button(text);
        button.setLayoutX(xPos);
        button.setLayoutY(BUTTON_Y);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setPrefWidth(BUTTON_HEIGHT*5);
        return button;
    }

    /**
     * Creates and returns the specific menu buttons to be displayed and used
     * @return- an ArrayList<Button> to be added to the display group in CAApp
     * Assumptions- the functions of these buttons will be set in CAApp before adding them to the display group
     */
    public ArrayList<Button> makeMenuButtons(){
        ResourceBundle myResources = ResourceBundle.getBundle("English");
        ArrayList<Button> menuButtons = new ArrayList<Button>();
        menuButtons.add(makeButton(myResources.getString("LoadNewSim"), NEW_SIM_X));
        menuButtons.add(makeButton("Pause", PAUSE_RESUME_X));
        menuButtons.add(makeButton("Step", STEP_X));
        menuButtons.add(makeButton("Slower", SLOWER_X));
        menuButtons.add(makeButton("Faster", FASTER_X));
        return menuButtons;
    }
}
