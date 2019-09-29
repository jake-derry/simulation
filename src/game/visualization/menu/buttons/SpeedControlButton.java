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
            myButton.setText(resources.getString("Faster"));
        }
        else{
            myButton.setText(resources.getString("Slower"));
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
<<<<<<< HEAD
<<<<<<< HEAD
                myAnimation.pause();
                myAnimation = new Timeline();
                if (mySim.getMillisecondDelay() > 50 || multiplier > 1){
                    mySim.setMillisecondDelay((int) (multiplier*mySim.getMillisecondDelay()));
                    startAnimation();
=======
                if (mySim.getDelay() > 50 || multiplier > 1){
                    mySim.startAnimation((int) (multiplier*mySim.getDelay()));
>>>>>>> master
                }
            }
        });
    }

<<<<<<< HEAD
    /**
     * startAnimation: stops the previously running animation and creates a new Timeline based on a perhaps updated MILLISECOND_DELAY
     */
    private void startAnimation(){
        myAnimation.pause();
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(mySim.getMillisecondDelay()), e -> mySim.step()));
        myAnimation.play();
    }
=======
>>>>>>> master
=======
                    myAnimation.setRate(multiplier);
            }
        });
    }
>>>>>>> origin/master
}
