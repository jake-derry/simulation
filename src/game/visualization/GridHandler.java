package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Matt Harris
 * This is a static class to control all of the display functions needed to display the grid of cells controlled by the simulation.
 * Dependencies: Cell, Simulation
 */
public class GridHandler {

    /**
     * Updates the colors of the polygons in the grid based on the new states of cells maintained by simulation.
     * @param polygonIterator- iterator to go through all polygons being displayed in grid
     * @param cellIterator- iterator to go through all cells maintained by simulation to get updated states
     * @param colorMap- the Map<State, Color> that allows us to know what color to update polygons to
     */
    public static void visualizeCells(Iterator<Shape> polygonIterator, Iterator<Cell> cellIterator, Map colorMap){
        while (polygonIterator.hasNext() && cellIterator.hasNext()){
            String stateKey = cellIterator.next().getState().toString();
            String colorVal = colorMap.get(stateKey).toString();
            polygonIterator.next().setFill(Paint.valueOf(colorVal));
        }
    }

    /**
     * This method creates all the polygon objects to be displayed in the grid (based on a number of sides) but does not set their colors.
     * @param windowSize- size of the display window to be used to calculate positions of the polygons
     * @param numRows- the number of rows of polygons to create
     * @param numCols- the number of columns of polygons to create
     * @param group- the group to add the polygons to
     * @param stylingMap- the map produced by Configurer reading a styling xml in order to get parameters used in creating polygons
     * @param numSides- the number of sides of the polygons to be created
     * @param mySim- the simulation to get the cells from in order to link them to polygons to increment states on polygon click event
     * @return- list of all polygons created
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
     * This method creates triangles in the case that the specified number of sides was 3.
     * @param i- the row index
     * @param j- the column index
     * @param cellWidth- width of the Cells
     * @param cellHeight- height of the Cells
     * @param menuHeight- the height of the Menu
     * @param xOffset- the offset to shift the triangles away from the left side of the window
     * @param odd- used to determine when to flip a triangle to effectively tile the screen
     * @param cell- the cell to link to the triangle so on click, the cell's state is incremented
     * @return- the created triangle
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
     This method creates rectangles in the case that the specified number of sides was 4.
     * @param i- the row index
     * @param j- the column index
     * @param cellWidth- width of the Cells
     * @param cellHeight- height of the Cells
     * @param menuHeight- the height of the Menu
     * @param xOffset- the offset to shift the rectangles away from the left side of the window
     * @param cell- the cell to link to the rectangle so on click, the cell's state is incremented
     * @return- the created rectangle
     **/

    private static Polygon createRectangle(int i, int j, double cellWidth, double cellHeight, double menuHeight, double xOffset, Cell cell){
        Polygon rectangle = new Polygon();
        double xPos = j*cellWidth + xOffset;
        double yPos = i*cellHeight+menuHeight;
        rectangle.getPoints().addAll(xPos, yPos, xPos, yPos+cellHeight, xPos+cellWidth, yPos+cellHeight, xPos+cellWidth, yPos);
        rectangle.setOnMouseClicked(mouseEvent -> cell.setState(cell.getNextStateOnClick(cell.getState())));
        return rectangle;
    }
}
