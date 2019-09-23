package game;

import javafx.scene.paint.Color;

/**
 * Visualization: created by a simulation in order to display the states in the cell grid.
 * Assumptions: at max, we will have 3 different unique states to be displayed for cells, represented by colorList
 * Dependencies: Cell
 * Use Case: a visualization is made for a simulation's gridOfCells when it is created and this will track and display the grid in the window
 * @author Matt Harris
 */
public class Visualization{
    private int WINDOW_SIZE;
    private int MENU_HEIGHT;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;
    private Cell[][] cellArray;
    private Color[] colorList;
    
    public Visualization(Cell[][] gridOfCells, int windowDimension){
        cellArray = gridOfCells;
        WINDOW_SIZE = windowDimension;
        MENU_HEIGHT = WINDOW_SIZE/4;
        CELL_WIDTH = WINDOW_SIZE / cellArray[0].length;
        CELL_HEIGHT = (WINDOW_SIZE - MENU_HEIGHT) / cellArray.length;
        colorList = new Color[4];
        colorList[0] = Color.BLACK;
        colorList[1] = Color.RED;
        colorList[2] = Color.YELLOW;
    }

    /**
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     * Assumptions: constant cellWidth / height
     */
    public void setUpRectangles(){
        for (int i = 0; i < cellArray.length; i++){
            for (int j = 0; j < cellArray[0].length; j++){
                cellArray[i][j].getRectangle().setHeight(CELL_HEIGHT);
                cellArray[i][j].getRectangle().setWidth(CELL_WIDTH);
                cellArray[i][j].getRectangle().setX(j*CELL_WIDTH+(WINDOW_SIZE-CELL_WIDTH*cellArray.length)/2);
                cellArray[i][j].getRectangle().setY(i*CELL_HEIGHT+MENU_HEIGHT);
            }
        }
    }

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public void visualize(){
        for (Cell[] cellRow : cellArray){
            for (Cell cell : cellRow){
                cell.stepState(colorList);
            }
        }
    }
}
