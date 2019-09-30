package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class SpeedControlButton extends MenuButton {
    private double multiplier;
    private Simulation mySim;
    private int millisecondDelay;
    private Timeline myAnimation;

    public SpeedControlButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim, int delay, Timeline animation, double speed){
        super(xPos, yPos, height, resources);
        multiplier = speed;
        mySim = sim;
        millisecondDelay = delay;
        myAnimation = animation;
        if (multiplier < 1){
            myButton.setText(resources.getString("Slower"));
        }
        else{
            myButton.setText(resources.getString("Faster"));
        }
        setButtonAction();
    }
    