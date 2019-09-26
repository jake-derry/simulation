package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import game.visualization.menu.buttons.MenuButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class SpeedControlButton extends MenuButton {
    private double multiplier;
    private Timeline myAnimation;
    private Simulation mySim;

    public SpeedControlButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim, Timeline animation, double speed){
        super(xPos, yPos, height, resources);
        multiplier = speed;
        myAnimation = animation;
        mySim = sim;
        if (multiplier < 1){
            myButton.setText(myResources.getString("Faster"));
        }
        else{
            myButton.setText(myResources.getString("Slower"));
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
                myAnimation.pause();
                myAnimation = new Timeline();
                if (mySim.getMILLISECOND_DELAY() > 50 || multiplier > 1){
                    mySim.setMILLISECOND_DELAY((int) (multiplier*mySim.getMILLISECOND_DELAY()));
                    startAnimation();
                }
            }
        });
    }

    /**
     * startAnimation: stops the previously running animation and creates a new Timeline based on a perhaps updated MILLISECOND_DELAY
     */
    private void startAnimation(){
        myAnimation.pause();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(mySim.getMILLISECOND_DELAY()), e -> mySim.step()));
        myAnimation.play();
    }
}
