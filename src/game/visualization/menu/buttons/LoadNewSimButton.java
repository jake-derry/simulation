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
    private Simulation mySim;
    private String myLanguage;

    /**
     *
     * @param xPos
     * @param yPos
     * @param height
     * @param resources
     * @param stage
     * @param sim
     * @param language
     */
    public LoadNewSimButton(int xPos, int yPos, int height, ResourceBundle resources, Stage stage, Simulation sim, String language){
        super(xPos, yPos, height, resources);
        myButton.setText(resources.getString("LoadNewSim"));
        myStage = stage;
        mySim = sim;
        myLanguage = language;
        setButtonAction();
    }

    @Override
    /**
     *
     */
    public void setButtonAction() {
        final FileChooser fileChooser = new FileChooser();
        myButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(final ActionEvent e) {
                File file = fileChooser.showOpenDialog(myStage);
                if (file != null) {
                    Stage stage = new Stage();
                    Timeline myAnimation = new Timeline();
                    Simulation sim = Configurer.getSimulation(file.getName());
                    Map stylingMap = Configurer.getStyling(sim.getParameterMap().get("StylingFile").toString());
                    Group group = new Group();
                    Visualization vis = new Visualization(group, sim, stage, myLanguage, myAnimation, stylingMap);
                    CAApp.addVisualization(vis);
                    CAApp.addSim(sim);
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
