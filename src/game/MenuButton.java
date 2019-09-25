package game;

import javafx.scene.control.Button;

abstract public class MenuButton {
    public Button myButton;

    public MenuButton(int xPos, int yPos, int size, String text){
        myButton = new Button(text);
        myButton.setLayoutX(xPos);
        myButton.setLayoutY(yPos);
        myButton.setPrefHeight(size);
        myButton.setPrefWidth(size*5);
    }

    protected abstract void setButtonAction();
}
