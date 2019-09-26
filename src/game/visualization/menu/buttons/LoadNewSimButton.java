package game.visualization.menu.buttons;

import game.CAApp;
import game.Configurer.Configurer;
import game.Simulation.Simulation;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ResourceBundle;

public class LoadNewSimButton extends MenuButton {
    private Stage myStage;
    private Simulation mySim;
    private int WINDOW_SIZE;
    private Group myGroup;
    private Timeline myAnimation;
    private String myLanguage;

    public LoadNewSimButton(int xPos, int yPos, int height, ResourceBundle resources, Stage stage, Group group, Simulation sim, Timeline animation, int windowSize, String language){
        super(xPos, yPos, height, resources);
        myButton.setText(myResources.getString("LoadNewSim"));
        myStage = stage;
        mySim = sim;
        myGroup = group;
        WINDOW_SIZE = windowSize;
        myAnimation = animation;
        myLanguage = language;
        setButtonAction();
    }

    @Override
    public void setButtonAction() {
        final FileChooser fileChooser = new FileChooser();
        myButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                    mySim = Configurer.getSimulation(file.getName(), WINDOW_SIZE, myLanguage);
                    mySim.setVisualization(myAnimation, myGroup, myStage, WINDOW_SIZE, myLanguage);
                    CAApp.setSim(mySim);
                }
            }
            });
    }
}
