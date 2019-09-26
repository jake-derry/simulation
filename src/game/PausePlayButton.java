package game;

import game.Simulation.Simulation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class PausePlayButton extends MenuButton {
    private boolean simRunning;
    private Simulation mySim;

    public PausePlayButton(int xPos, int yPos, int height, ResourceBundle resources, Simulation sim) {
        super(xPos, yPos, height, resources);
        mySim = sim;
        myButton.setText(myResources.getString("Pause"));
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
                if (simRunning) {
                    mySim.pause();
                    getButton().setText("Play");
                    simRunning = false;
                } else {
                    mySim.play();
                    getButton().setText("Pause");
                    simRunning = true;
                }
            }
        });
    }
}
