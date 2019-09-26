package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class SpeedControlButton extends MenuButton {
    private double multiplier;
    private Simulation mySim;

    public SpeedControlButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim, double speed){
        super(xPos, yPos, height, resources);
        multiplier = speed;
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
                if (mySim.getDelay() > 50 || multiplier > 1){
                    mySim.startAnimation((int) (multiplier*mySim.getDelay()));
                }
            }
        });
    }

}
