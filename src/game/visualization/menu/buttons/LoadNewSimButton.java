package game.visualization.menu.buttons;

import game.CAApp;
import game.Configurer.Configurer;
import game.Simulation.Simulation;
import game.visualization.Visualization;
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
    private Visualization myVis;
    private int WINDOW_SIZE;
    private Group myGroup;
    private String myLanguage;
    private Timeline myAnimation;

    public LoadNewSimButton(int xPos, int yPos, int height, ResourceBundle resources, Stage stage, Group group, Simulation sim, int windowSize, String language, Timeline animation){
        super(xPos, yPos, height, resources);
        myButton.setText(resources.getString("LoadNewSim"));
        myStage = stage;
        mySim = sim;
        myGroup = group;
        WINDOW_SIZE = windowSize;
        myLanguage = language;
        myAnimation = animation;
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
                    mySim = Configurer.getSimulation(file.getName());
                    myVis = new Visualization(myGroup, mySim, myStage, WINDOW_SIZE, myLanguage, myAnimation);
                    CAApp.setVisualization(myVis);
                    CAApp.setSim(mySim);
                }
            }
            });
    }
}
