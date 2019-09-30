package game.visualization;

import game.Simulation.Cell.Cell;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridHandler {

    /**
     * visualize: step through cells and update colors accordingly
     * Assumptions: N/A
     */
    public static void visualizeCells(Iterator<Shape> rectangleIterator, Iterator<Cell> cellIterator, Map colorMap){
        while (rectangleIterator.hasNext() && cellIterator.hasNext()){
            int x =0;
            String stateKey = cellIterator.next().getState().toString();
            String colorVal = colorMap.get(stateKey).toString();
            rectangleIterator.next().setFill(Paint.valueOf(colorVal));
        }
    }

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
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     * Assumptions: constant cellWidth / height
     */
    public static List setUpPolygons(int windowSize, int numRows, int numCols, Group group, Map stylingMap, int numSides){
        boolean colOdd = true;
        boolean rowOdd = true;
        ArrayList polygonList = new ArrayList<Polygon>();
        double MENU_HEIGHT = windowSize/4;
        double CELL_HEIGHT = (windowSize - MENU_HEIGHT) / numRows;
        double CELL_WIDTH = windowSize*4/5 / numCols;
        double xOffset = (windowSize - CELL_WIDTH*numCols)/2;
        if (stylingMap.containsKey("cellSize") && (int)stylingMap.get("cellSize")*numCols < windowSize/2){
            CELL_HEIGHT = (int)stylingMap.get("cellSize");
            CELL_WIDTH = CELL_HEIGHT;
        }
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Polygon polygon = new Polygon();
                switch(numSides){
                    case 3:
                    {
                        polygon = createTriangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset, colOdd);
                        break;
                    }
                    case 4:{
                        polygon = createRectangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset);
                        break;
                    }
                    default:{
                        polygon = createRectangle(i,j,CELL_WIDTH,CELL_HEIGHT, MENU_HEIGHT, xOffset);
                    }
                }
                if (stylingMap.containsKey("outline") && (int)stylingMap.get("outline") == 1){
                    polygon.setStroke(Color.BLACK);
                }
                polygonList.add(polygon);
                group.getChildren().add(polygon);
                colOdd = !colOdd;
            }
            rowOdd = !rowOdd;
        }
        return polygonList;
    }

    private static Polygon createTriangle(int i, int j, double cellWidth, double cellHeight, double menuHeight, double xOffset, boolean odd){
        Polygon triangle = new Polygon();
        Double xPos = j*cellWidth/2 + xOffset*2;
        Double yPos = i*cellHeight+menuHeight;
        if (odd){
            triangle.getPoints().addAll(xPos, yPos, xPos+cellWidth*0.5, yPos+cellHeight, xPos+cellWidth, yPos);
        }
        else{
            triangle.getPoints().addAll(xPos, yPos+cellHeight, xPos+cellWidth/2, yPos, xPos+cellWidth, yPos+cellHeight);
        }
        return triangle;
    }

    private static Polygon createRectangle(int i, int j, double cellWidth, double cellHeight, double menuHeight, double xOffset){
        Polygon rectangle = new Polygon();
        double xPos = j*cellWidth + xOffset;
        double yPos = i*cellHeight+menuHeight;
        rectangle.getPoints().addAll(xPos, yPos, xPos, yPos+cellHeight, xPos+cellWidth, yPos+cellHeight, xPos+cellWidth, yPos);
        return rectangle;
    }
}
