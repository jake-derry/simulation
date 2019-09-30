package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import game.Simulation.State;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GridHandler {

    /**
     *
     * @param rectangleIterator
     * @param cellIterator
     * @param colorMap
     */
    public static void visualizeCells(Iterator<Shape> rectangleIterator, Iterator<Cell> cellIterator, Map colorMap){
        while (rectangleIterator.hasNext() && cellIterator.hasNext()){
            String stateKey = cellIterator.next().getState().toString();
            String colorVal = colorMap.get(stateKey).toString();
            rectangleIterator.next().setFill(Paint.valueOf(colorVal));
        }
    }

    /**
     *
     * @param windowSize
     * @param numRows
     * @param numCols
     * @param group
     * @param stylingMap
     * @param numSides
     * @param mySim
     * @return
     */
    public static List setUpPolygons(int windowSize, int numRows, int numCols, Group group, Map stylingMap, int numSides, Simulation mySim){
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
        Iterator<Cell> cellIterator = mySim.getGrid().iterator();
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Cell cell = cellIterator.next();
                Polygon polygon = new Polygon();
                switch(numSides){
                    case 3:
                    {
                        polygon = createTriangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset, colOdd, cell);
                        break;
                    }
                    case 4:{
                        polygon = createRectangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset, cell);
                        break;
                    }
                    default:{
                        polygon = createRectangle(i,j,CELL_WIDTH,CELL_HEIGHT, MENU_HEIGHT, xOffset, cell);
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

    /**
     *
     * @param i
     * @param j
     * @param cellWidth
     * @param cellHeight
     * @param menuHeight
     * @param xOffset
     * @param odd
     * @param cell
     * @return
     */
    private static Polygon createTriangle(int i, int j, double cellWidth, double cellHeight, double menuHeight, double xOffset, boolean odd, Cell cell){
        Polygon triangle = new Polygon();
        Double xPos = j*cellWidth/2 + xOffset*2;
        Double yPos = i*cellHeight+menuHeight;
        if (odd){
            triangle.getPoints().addAll(xPos, yPos, xPos+cellWidth*0.5, yPos+cellHeight, xPos+cellWidth, yPos);
        }
        else{
            triangle.getPoints().addAll(xPos, yPos+cellHeight, xPos+cellWidth/2, yPos, xPos+cellWidth, yPos+cellHeight);
        }
        triangle.setOnMouseClicked(mouseEvent -> cell.setState(cell.getNextStateOnClick(cell.getState())));
        return triangle;
    }

    /**
     *
     * @param i
     * @param j
     * @param cellWidth
     * @param cellHeight
     * @param menuHeight
     * @param xOffset
     * @param cell
     * @return
     */
    private static Polygon createRectangle(int i, int j, double cellWidth, double cellHeight, double menuHeight, double xOffset, Cell cell){
        Polygon rectangle = new Polygon();
        double xPos = j*cellWidth + xOffset;
        double yPos = i*cellHeight+menuHeight;
        rectangle.getPoints().addAll(xPos, yPos, xPos, yPos+cellHeight, xPos+cellWidth, yPos+cellHeight, xPos+cellWidth, yPos);
        rectangle.setOnMouseClicked(mouseEvent -> cell.setState(cell.getNextStateOnClick(cell.getState())));
        return rectangle;
    }
}
