package game.visualization;

import game.Simulation.Cell.Cell;
import game.Simulation.Simulation;
import javafx.scene.Group;
import javafx.scene.paint.Color;

public class GridHandler {
    private Simulation mySim;
    private Group myGroup;
    private final int CELL_WIDTH;
    private final int CELL_HEIGHT;
    private final int WINDOW_SIZE;
    private final int MENU_HEIGHT;

    public GridHandler(Simulation sim, Group group, int size){
        mySim = sim;
        myGroup = group;
        WINDOW_SIZE = size;
        MENU_HEIGHT = WINDOW_SIZE/4;
        CELL_WIDTH = WINDOW_SIZE / mySim.getGridColumnCount();
        CELL_HEIGHT = (WINDOW_SIZE - MENU_HEIGHT) / mySim.getGridRowCount();
        setUpRectangles();
    }

    /**
     * setUpRectangles: sets width, height, x, and y coordinates of rectangles for cells
     * Assumptions: constant cellWidth / height
     */
    private void setUpRectangles(){
        for (int i = 0; i < mySim.getGrid().length; i++){
            for (int j = 0; j < mySim.getGrid()[0].length; j++){
                mySim.getGrid()[i][j].getRectangle().setHeight(CELL_HEIGHT);
                mySim.getGrid()[i][j].getRectangle().setWidth(CELL_WIDTH);
                mySim.getGrid()[i][j].getRectangle().setX(j*CELL_WIDTH+(WINDOW_SIZE-CELL_WIDTH*mySim.getGrid().length)/2);
                mySim.getGrid()[i][j].getRectangle().setY(i*CELL_HEIGHT+MENU_HEIGHT);
                myGroup.getChildren().add(mySim.getGrid()[i][j].getRectangle());
            }
        }
    }
}
