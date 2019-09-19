package game;

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
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;

    /**
     * Constructor for Visualization
     */
    public Visualization(Cell[][] gridOfCells, Map<String, Object> configVals, int windowDimension){
        cellArray = gridOfCells;
        WINDOW_SIZE = windowDimension;

        // Cell dimensions
        MENU_HEIGHT = WINDOW_SIZE/4;
        CELL_WIDTH = WINDOW_SIZE / cellArray[0].length;
        CELL_HEIGHT = (WINDOW_SIZE - MENU_HEIGHT) / cellArray.length;

        //This can all be changed once we know a bit better how we want to instantiate our color list
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.BLUE;
        colorList[2] = Color.RED;
        colorList[3] = Color.WHITE;
    }

    /**
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     */
    public void setUpRectangles(){
        // This is a comment
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
     * visualize: step through cells and update colors accordingly
     */
    public void visualize(){
        for (Cell[] cellRow : cellArray){
            for (Cell cell : cellRow){
                cell.stepState(colorList);
            }
        }
    }
}
