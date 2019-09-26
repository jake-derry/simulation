package game;

import javafx.scene.control.Button;

import java.util.ResourceBundle;

abstract public class MenuButton {
    protected Button myButton;
    protected ResourceBundle myResources;


    public MenuButton(int xPos, int yPos, int height, ResourceBundle resources){
        myButton = new Button();
        myButton.setLayoutX(xPos);
        myButton.setLayoutY(yPos);
        myButton.setPrefHeight(height);
        myButton.setPrefWidth(height*5);
        myResources = resources;
    }

    public Button getButton(){
        return myButton;
    }

    protected abstract void setButtonAction();
}
