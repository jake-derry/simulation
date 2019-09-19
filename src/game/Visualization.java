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
    private int WINDOW_SIZE;
    private int MENU_HEIGHT;
    // Stuff for calculating rectangle position / size
    private final int CELL_WIDTH = WINDOW_SIZE / cellArray[0].length;
    private final int CELL_HEIGHT = (WINDOW_SIZE - MENU_HEIGHT) / cellArray.length;

    /**
     * Constructor for Visualization
     */
    public Visualization(Cell[][] groupOfCells, Map<String, Object> configVals, int windowDimension){
        cellArray = groupOfCells;
        WINDOW_SIZE = windowDimension;
        MENU_HEIGHT = WINDOW_SIZE/4;

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
    public void setUpRectangles(){
        for (int i = 0; i < cellArray.length; i++){
            for (int j = 0; j < cellArray[0].length; j++){
                cellArray[i][j].getRectangle().setHeight(CELL_HEIGHT);
                cellArray[i][j].getRectangle().setWidth(CELL_WIDTH);
                cellArray[i][j].getRectangle().setX(j*CELL_WIDTH);
                cellArray[i][j].getRectangle().setY(i*CELL_HEIGHT);
            }
        }
    }

    /**
     *
     */

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
