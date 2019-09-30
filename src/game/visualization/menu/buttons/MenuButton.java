package game.visualization.menu.buttons;

import javafx.scene.control.Button;

import java.util.ResourceBundle;

abstract public class MenuButton {
    protected Button myButton;

    /**
     *
     * @param xPos
     * @param yPos
     * @param height
     * @param resources
     */
    public MenuButton(int xPos, int yPos, int height, ResourceBundle resources){
        myButton = new Button();
        myButton.setLayoutX(xPos);
        myButton.setLayoutY(yPos);
        myButton.setPrefHeight(height);
        myButton.setPrefWidth(height*5);
    }

    /**
     *
     * @return
     */
    public Button getButton(){
        return myButton;
    }

    /**
     *
     */
    protected abstract void setButtonAction();
}
