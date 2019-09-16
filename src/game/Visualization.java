package game;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.util.Map;


/**
 * Visualization section of our CAApp.
 *
 * @author Matt Harris
 */
public class Visualization{
    private Cell[][] cellArray;
    private Color[] colorList;

    /**
     * Constructor for Visualization
     */
    public Visualization(Cell[][] groupOfCells, Map<String, Object> configVals){
        cellArray = groupOfCells;

        //This can all be changed once we know a bit better how we want to instantiate our color list
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.BLUE;
        colorList[2] = Color.RED;
        colorList[3] = Color.WHITE;
    }

    /**
     * setUp the rectangles to be displayed in CAApp
     */
    public Group setUpRectangles(Node root, ){
        Group group = new Group();

        return group;
    }

    /**
     * visualize: step through cells and update colors accordingly
     */
    public void visualize(Color[] colors){
        for (Cell[] cellRow : cellArray){
            for (Cell cell : cellRow){
                cell.stepState(colors);
            }
        }
    }
}
