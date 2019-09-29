package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import game.visualization.menu.MenuHandler;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Visualization: created by a simulation in order to display the states in the cell grid.
 * Assumptions: at max, we will have 3 different unique states to be displayed for cells, represented by colorList
 * Dependencies: Cell
 * Use Case: a visualization is made for a simulation's gridOfCells when it is created and this will track and display the grid in the window
 * @author Matt Harris
 */
public class Visualization{
    private String DEFAULT_RESOURCE_PACKAGE = "/data/";
    private Color[] colorList;

    private Group myGroup;
    private MenuHandler myMenuHandler;
    private GridHandler myGridHandler;
    private Simulation mySim;
    
    public Visualization(Group group, Simulation sim, Stage stage, int windowDimension, String language){
        myGroup = group;
        myGroup.getChildren().clear();
        mySim = sim;
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.RED;
        colorList[2] = Color.YELLOW;
        myMenuHandler = new MenuHandler(myGroup, mySim, windowDimension, stage, language);
        myGridHandler = new GridHandler(mySim, myGroup, windowDimension);
    }

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public void visualize(){
        for (Cell[] cellRow : mySim.getGrid()){
            for (Cell cell : cellRow){
                cell.stepState(colorList);
            }
        }
    }

}
