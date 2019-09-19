package game;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class DisplayHandler {
    // Stuff for calculating menu positions
    private final int MENU_HEIGHT;
    private final int WINDOW_SIZE;
    private final int TITLE_X;
    private final int TITLE_Y;
    private final int TITLE_SIZE;
    private final int NEW_SIM_X;
    private final int PAUSE_RESUME_X;
    private final int STEP_X;
    private final int SIM_SPEED_X;
    private final int BUTTON_TEXT_Y;
    private final int BUTTON_TEXT_SIZE;

    public DisplayHandler(int windowDimension){
        // Menu Item Positions
        WINDOW_SIZE = windowDimension;
        MENU_HEIGHT = WINDOW_SIZE/4;
        TITLE_X = WINDOW_SIZE/16;
        TITLE_Y = MENU_HEIGHT/4;
        TITLE_SIZE = MENU_HEIGHT/4;
        NEW_SIM_X = TITLE_X;
        PAUSE_RESUME_X = WINDOW_SIZE*2/5;
        STEP_X = WINDOW_SIZE*3/5;
        SIM_SPEED_X = WINDOW_SIZE*4/5;
        BUTTON_TEXT_SIZE = TITLE_SIZE/2;
        BUTTON_TEXT_Y = MENU_HEIGHT*3/4;
    }

    /**
     * setUpMenuItems: sets up title and position/size of menu items.
     */
    public void setUpMenuItems(String simTitle, ArrayList<Node> menuItems){
        Text titleText = createText(TITLE_X, TITLE_Y, simTitle, TITLE_SIZE);
        menuItems.add(titleText);
        Text newSimButtonText = createText(NEW_SIM_X, BUTTON_TEXT_Y, "New Simulation", BUTTON_TEXT_SIZE);
        menuItems.add(newSimButtonText);
        Text pauseResumeButtonText = createText(PAUSE_RESUME_X, BUTTON_TEXT_Y, "New Simulation", BUTTON_TEXT_SIZE);
        menuItems.add(pauseResumeButtonText);
        Text stepButtonText = createText(STEP_X, BUTTON_TEXT_Y, "New Simulation", BUTTON_TEXT_SIZE);
        menuItems.add(stepButtonText);
        Text simSpeedButtonText = createText(SIM_SPEED_X, BUTTON_TEXT_Y, "Simulation Speed", BUTTON_TEXT_SIZE);
        menuItems.add(simSpeedButtonText);
    }

    /**
     * Helps with creating text of a particular size at a particular position
     * @param xpos
     * @param ypos
     * @param text
     * @param fontSize
     * @return
     */
    private Text createText(int xpos, int ypos, String text, int fontSize){
        Text finalText = new Text(xpos, ypos, text);
        finalText.setFont(new Font(fontSize));
        return finalText;
    }

}
