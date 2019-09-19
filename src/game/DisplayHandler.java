package game;

import game.simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
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
    private final int BUTTON_Y;
    private final int BUTTON_DIMENSION;
    private Simulation mySim;
    private Configurer myConfig;
    private Group myGroup;
    private boolean simRunning;

    public DisplayHandler(Configurer config, Simulation sim, Group group, int windowDimension){
        myConfig = config;
        mySim = sim;
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
        BUTTON_Y = MENU_HEIGHT/2;
        BUTTON_DIMENSION = WINDOW_SIZE / 10;
    }

    /**
     * setUpMenuItems: sets up title and position/size of menu items.
     */
    public void setUpMenuText(ArrayList<Node> menuItems){
        Text titleText = createText(TITLE_X, TITLE_Y, mySim.getSimTitle(), TITLE_SIZE);
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
    /*
    public void setUpMenuButtons(){
        // Can almost certainly make some subclasses for these buttons
        // NEW SIM BUTTON
        Button newSimButton = new Button("newSimButton", new ImageView("plus.png"));
        newSimButton.setLayoutX(NEW_SIM_X);
        newSimButton.setLayoutY(BUTTON_Y);
        newSimButton.setPrefHeight(BUTTON_DIMENSION);
        newSimButton.setPrefWidth(BUTTON_DIMENSION);
        newSimButton.setOnAction(new EventHandler<ActionEvent>(){
            // TODO: FIGURE OUT HOW TO DO THIS BUTTON'S BEHAVIOR
            @Override
            public void handle(ActionEvent event) {
            }
        });
        myGroup.getChildren().add(newSimButton);

        // PAUSE RESUME BUTTON
        Button pauseResumeButton = new Button("pauseResumeButton", new ImageView("pause.png"));
        pauseResumeButton.setLayoutX(PAUSE_RESUME_X);
        newSimButton.setLayoutY(BUTTON_Y);
        newSimButton.setPrefHeight(BUTTON_DIMENSION);
        newSimButton.setPrefWidth(BUTTON_DIMENSION);
        newSimButton.setOnAction(new EventHandler<ActionEvent>(){
            // TODO: FIGURE OUT HOW TO DO THIS BUTTON'S BEHAVIOR
            @Override
            public void handle(ActionEvent event) {
                if()
                mySim.play()
            }
        });
        myGroup.getChildren().add(newSimButton);

    }*/

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
