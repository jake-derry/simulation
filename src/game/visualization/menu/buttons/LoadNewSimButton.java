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

import static game.CAApp.step;
import static javafx.scene.paint.Color.BLACK;

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
                    Stage stage = new Stage();
                    Timeline myAnimation = new Timeline();
                    Simulation sim = Configurer.getSimulation(file.getName());
                    Map stylingMap = Configurer.getStyling(mySim.getParameterMap().get("StylingFile").toString());
                    Group group = new Group();
                    Visualization vis = new Visualization(group, sim, stage, myLanguage, myAnimation, stylingMap);
                    CAApp.addVisualization(vis);
                    CAApp.addSim(sim);
                    var frame = new KeyFrame(Duration.millis(vis.getDelay()), f -> step(1));
                    myAnimation.setCycleCount(Timeline.INDEFINITE);
                    myAnimation.getKeyFrames().add(frame);
                    myAnimation.play();
                    stage.setScene(new Scene(group, vis.getWindowWidth(), vis.getWindowHeight(), BLACK));
                    stage.show();
                }
            }
            });
    }
}
