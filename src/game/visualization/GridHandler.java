package game.visualization;

import game.Simulation.Cell.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridHandler {

    /**
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     * Assumptions: constant cellWidth / height
     */
    public static List setUpRectangles(int windowSize, int numRows, int numCols, Group group, Map stylingMap){
        ArrayList rectangleList = new ArrayList<Rectangle>();
        int MENU_HEIGHT = windowSize/4;
        int CELL_HEIGHT = (windowSize - MENU_HEIGHT) / numRows;
        int CELL_WIDTH = windowSize*4/5 / numCols;
        if (stylingMap.containsKey("cellSize") && (int)stylingMap.get("cellSize")*numCols < windowSize/2){
            CELL_HEIGHT = (int)stylingMap.get("cellSize");
            CELL_WIDTH = CELL_HEIGHT;
        }
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                int xPos = j*CELL_WIDTH+(windowSize-CELL_WIDTH*numCols)/2;
                int yPos = i*CELL_HEIGHT+MENU_HEIGHT;
                Rectangle rectangle = new Rectangle(xPos, yPos, CELL_WIDTH, CELL_HEIGHT);
                if (stylingMap.containsKey("outline") && (int)stylingMap.get("outline") == 1){
                    rectangle.setStroke(Color.BLACK);
                }
                rectangleList.add(rectangle);
                group.getChildren().add(rectangle);
            }
        }
        return rectangleList;
    }

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public static void visualizeCells(Iterator<Rectangle> rectangleIterator, Iterator<Cell> cellIterator, Map colorMap){
            while (rectangleIterator.hasNext() && cellIterator.hasNext()){
                String stateKey = cellIterator.next().getState().toString();
                String colorVal = colorMap.get(stateKey).toString();
                rectangleIterator.next().setFill(Paint.valueOf(colorVal));
            }
    }
}
