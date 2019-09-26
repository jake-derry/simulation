package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class StepButton extends MenuButton {

    private Simulation mySim;
    public StepButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim){
        super(xPos, yPos, height, resources);
        mySim = sim;
        myButton.setText(myResources.getString("Step"));
        setButtonAction();
    }

    @Override
    public void setButtonAction() {
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                if (!mySim.getSimRunning()){
                    mySim.play();
                    mySim.step();
                    mySim.pause();
                }
            }
        });
    }
}
