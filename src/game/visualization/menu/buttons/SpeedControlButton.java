package game.visualization.menu.buttons;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class SpeedControlButton extends MenuButton {
    private double multiplier;
    private Timeline myAnimation;

    /**
     * @author Matt Harris
     * This class implements MenuButton and represents a button capable of controlling the speed of the progress of the simulation.
     * Assumptions: the button width will always be twice the specified button height
     * Dependencies: Simulation, Configurer, Visualization, CAApp
     * @param xPos- xPosition in the scene for the button to appear
     * @param yPos- yPosition in the scene for the button to appear
     * @param height- height of the button to be created
     * @param resources- the ResourcesBundle to pull the text from
     * @param animation- the Timeline to be controlled by the button
     * @param speedMultiplier- the double to be multiplied to the rate of the Timeline in order to affect the speed of animation.
     */
    public SpeedControlButton(int xPos, int yPos, int height, ResourceBundle resources, Timeline animation, double speedMultiplier){
        super(xPos, yPos, height, resources);
        multiplier = speedMultiplier;
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
     * Sets the action of a SpeedControlButton
     */
    @Override
    protected void setButtonAction() {
        myButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                myAnimation.setRate(myAnimation.getRate()*multiplier);
            }
        });
    }
}
