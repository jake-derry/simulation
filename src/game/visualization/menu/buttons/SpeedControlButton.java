package game.visualization.menu.buttons;

import game.Simulation.Simulation;
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

    /**
     * setSpeedHandler: Sets the specfic button action for the Simulation Speed Buttons
     * @return- event handler for Sim Speed Buttons
     * Note: a check had to added in order to keep the speed from being increased to a point of crashing the app
     */
    @Override
    public void setButtonAction() {
        myButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                myAnimation.setRate(myAnimation.getRate()*multiplier);
            }
        });
    }
}
