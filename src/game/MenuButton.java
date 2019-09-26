package game;

import javafx.scene.control.Button;

import java.util.ResourceBundle;

abstract public class MenuButton {
    protected Button myButton;
    protected ResourceBundle myResources;


    public MenuButton(int xPos, int yPos, int size, ResourceBundle resources){
        myButton = new Button();
        myButton.setLayoutX(xPos);
        myButton.setLayoutY(yPos);
        myButton.setPrefHeight(size);
        myButton.setPrefWidth(size*5);
        myResources = resources;
    }

    protected abstract void setButtonAction();
}
