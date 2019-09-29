package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

public class GridHandler {

    /**
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     * Assumptions: constant cellWidth / height
     */
    public static Iterator<Rectangle> setUpRectangles(int windowSize, int numRows, int numCols, Group group){
        ArrayList rectangleList = new ArrayList<Rectangle>();
        int MENU_HEIGHT = windowSize/4;
        int CELL_HEIGHT = (windowSize - MENU_HEIGHT) / numRows;
        int CELL_WIDTH = windowSize / numCols;
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                int xPos = j*CELL_WIDTH+(windowSize-CELL_WIDTH*numRows)/2;
                int yPos = i*CELL_HEIGHT+MENU_HEIGHT;
                Rectangle rectangle = new Rectangle(xPos, yPos, CELL_WIDTH, CELL_HEIGHT);
                rectangleList.add(rectangle);
                group.getChildren().add(rectangle);
            }
        }
        return rectangleList.iterator();
    }

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public static void visualizeCells(Iterator<Rectangle> rectangleIterator, Simulation sim, Color[] colorList){
        Iterator<Cell> cellIterator = sim.getGrid().iterator();
        while (rectangleIterator.hasNext() && cellIterator.hasNext()){
            //rectangleIterator.next().setFill(colorList[cellIterator.next().getState()]);
            rectangleIterator.next().setFill(Color.BLUE);
        }
    }
}
