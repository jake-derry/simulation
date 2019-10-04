package game.visualization.menu.buttons;

import game.CAApp;
import game.Configurer.Configurer;
import game.Simulation.Simulation;
import game.visualization.Visualization;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.Map;
import java.util.ResourceBundle;

public class LoadNewSimButton extends MenuButton {
    private Stage myStage;
    private String myLanguage;

    /**
     * @author Matt Harris
     * This class implements MenuButton and represents a button capable of launching a new sim, given a user's choice of xml file, in a new window.
     * Assumptions: the user always wants the new simulation to appear in a new window and with the same language resources as the simulation that contains this button
     * Assumptions: the button width will always be twice the specified button height
     * Dependencies: depends on Simulation, Configurer, Visualization, CAApp
     * @param xPos- xPosition in the scene for the button to appear
     * @param yPos- yPosition in the scene for the button to appear
     * @param height- height of the button to be created
     * @param resources- the ResourcesBundle to pull the text from
     * @param stage- the stage to open the file chooser in
     * @param language- the language of the simulation to be used in setting up the newly created simulation/visualization
     */
    public LoadNewSimButton(int xPos, int yPos, int height, ResourceBundle resources, Stage stage, String language){
        super(xPos, yPos, height, resources);
        myButton.setText(resources.getString("LoadNewSim"));
        myStage = stage;
        myLanguage = language;
        setButtonAction();
    }

    @Override
    /**
     * This method sets the action of the LoadNewSim button when clicked.
     * Assumptions: see above
     */
    protected void setButtonAction() {
        final FileChooser fileChooser = new FileChooser();
        myButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                    Stage stage = new Stage();
                    Timeline myAnimation = new Timeline();
                    Simulation sim = Configurer.getSimulation("simulations/" + file.getName());
                    Map stylingMap = Configurer.getStyling(sim.getParameterMap().get("StylingFile").toString());
                    Group group = new Group();
                    Visualization vis = new Visualization(group, sim, stage, myLanguage, myAnimation, stylingMap);
                    var frame = new KeyFrame(Duration.millis(vis.getDelay()), f -> vis.step());
                    myAnimation.setCycleCount(Timeline.INDEFINITE);
                    myAnimation.getKeyFrames().add(frame);
                    myAnimation.play();
                    stage.setScene(new Scene(group, vis.getWindowWidth(), vis.getWindowHeight(), vis.getBackgroundColor()));
                    stage.show();
                }
            }
            });
    }
}
