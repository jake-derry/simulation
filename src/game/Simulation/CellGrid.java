package game.Simulation;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.FireCell;
import game.Simulation.Cell.GameOfLifeCell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;
    private static final int[] NEIGHBORS = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
    private static final int[][] DISPLACEMENTS = new int[][] {
            new int[] {1, -1},
            new int[] {0, 1},
            new int[] {1, 1},
            new int[] {1, 0},
            new int[] {-1, 0},
            new int[] {-1, -1},
            new int[] {0, -1},
            new int[] {1, -1}
    };

    // assumes rectangle grid shape for the moment
    public CellGrid(Map<String, Object> parameterMap, String[][] cellGrid) {
        createCellGrid(cellGrid);
        connectNeighbors(cellGrid);
    }

    //TODO WE DONT WANT TO HARD CODE TYPES
    private void createCellGrid(String[][] cellGrid) {
        myCellGrid = new Cell[cellGrid.length][cellGrid[0].length];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                myCellGrid[i][j] = new FireCell(State.getState(cellGrid[i][j]), 0.3);
            }
        }
    }

    private void connectNeighbors(String[][] cellGrid) {
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                List<Cell> neighborhood = new ArrayList<>();
                for (int neighborDisplacement : NEIGHBORS) {
                    int[] displacement = DISPLACEMENTS[neighborDisplacement];
                    int iNeighbor = i + displacement[0];
                    int jNeighbor = j + displacement[1];
                    if (inXRange(iNeighbor) && inYRange(jNeighbor)) {
                        Cell neighbor = myCellGrid[iNeighbor][jNeighbor];
                        neighborhood.add(neighbor);
                    }
                }

                myCellGrid[i][j].setNeighbors(neighborhood.iterator());
            }
        }
    }

    private boolean inYRange(int jNeighbor) {
        return jNeighbor >= 0 && jNeighbor < myCellGrid.length;
    }

    private boolean inXRange(int iNeighbor) {
        return iNeighbor >= 0 && iNeighbor < myCellGrid[0].length;
    }

    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(myCellGrid);
    }

    public int getCellRows(){
        return myCellGrid.length;
    }

    public int getCellColumns(){
        return myCellGrid[0].length;
    }
}