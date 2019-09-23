package game;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

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
        // Menu Item Positions
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
     * Helps with creating text of a particular size at a particular position
     * @param text
     * @return
     */
    public Text createText(String text){
        Text finalText = new Text(TITLE_X, TITLE_Y, text);
        finalText.setFont(new Font(TITLE_SIZE));
        return finalText;
    }

    /**
     * @param text
     * @param xPos
     * @return
     */
    private Button makeButton(String text, int xPos){
        Button button = new Button(text);
        button.setLayoutX(xPos);
        button.setLayoutY(BUTTON_Y);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setPrefWidth(BUTTON_HEIGHT*5);
        return button;
    }

    public ArrayList<Button> makeMenuButtons(){
        ArrayList<Button> menuButtons = new ArrayList<Button>();
        menuButtons.add(makeButton("New Sim", NEW_SIM_X));
        menuButtons.add(makeButton("Pause", PAUSE_RESUME_X));
        menuButtons.add(makeButton("Step", STEP_X));
        menuButtons.add(makeButton("Slower", SLOWER_X));
        menuButtons.add(makeButton("Faster", FASTER_X));
        return menuButtons;
    }
}
