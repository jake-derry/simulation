package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class PausePlayButton extends MenuButton {
    private Simulation mySim;
    private ResourceBundle myResources;

    /**
     * @author Matt Harris
     * This class implements MenuButton and represents a button capable of pausing and playing the simulation,
     * stopping the progress and changing the displayed button text between 'Pause' and 'Play'.
     * Assumptions: a pause / play button will only be able to control a single simulation, not all that are open in all windows.
     * Dependencies: Simulation
     * @param xPos- xPosition in the scene for the button to appear
     * @param yPos- yPosition in the scene for the button to appear
     * @param height- height of the button to be created
     * @param resources- the ResourcesBundle to pull the text from
     * @param sim- simulation being controlled by the pause / play button
     */
    public PausePlayButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim) {
        super(xPos, yPos, height, resources);
        mySim = sim;
        myResources = resources;
        myButton.setText(myResources.getString("Play"));
        setButtonAction();
    }

    /**
     * Sets the action of the pause / play button
     */
    @Override
    protected void setButtonAction() {
        myButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent e) {
                if (mySim.getSimRunning()) {
                    mySim.pause();
                    getButton().setText(myResources.getString("Play"));
                } else {
                    mySim.play();
                    getButton().setText(myResources.getString("Pause"));
                }
            }
        });
    }
}
