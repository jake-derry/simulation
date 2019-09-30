package game.visualization.menu.buttons;

import game.Simulation.Simulation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ResourceBundle;

public class PausePlayButton extends MenuButton {
    private Simulation mySim;
    private ResourceBundle myResources;

    /**
     *
     * @param xPos
     * @param yPos
     * @param height
     * @param resources
     * @param sim
     */
    public PausePlayButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim) {
        super(xPos, yPos, height, resources);
        mySim = sim;
        myResources = resources;
        myButton.setText(myResources.getString("Play"));
        setButtonAction();
    }

    /**
     * setPauseResumeHandler: Sets the specific button action for the Pause/Resume Button
     *
     * @return- event handler for Pause/Resume Button
     */
    @Override
    public void setButtonAction() {
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
