package game.visualization.menu.buttons;

import javafx.scene.control.Button;

import java.util.ResourceBundle;

abstract public class MenuButton {
    protected Button myButton;
    protected ResourceBundle myResources;

    /**
     * @author Matt Harris
     * This is an abstract class for the MenuButtons of our app. Does the work of setting up the location and size of the button but not the functionality.
     * Assumptions: the button width will always be twice the specified button height
     * @param xPos- xPosition in the scene for the button to appear
     * @param yPos- yPosition in the scene for the button to appear
     * @param height- height of the button to be created
     */
    public MenuButton(int xPos, int yPos, int height, ResourceBundle resources){
        myButton = new Button();
        myButton.setLayoutX(xPos);
        myButton.setLayoutY(yPos);
        myButton.setPrefHeight(height);
        myButton.setPrefWidth(height*5);
        myResources = resources;
    }

    /**
     * Gets the javafx button object associated with the MenuButton
     * @return- Button associated with the MenuButton object
     */
    public Button getButton(){
        return myButton;
    }

    /**
     * Sets the particular action of a button to be implemented differently for each subclass of MenuButton.
     */
    protected abstract void setButtonAction();
}
