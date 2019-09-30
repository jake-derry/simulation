package game.Simulation;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.FireCell;
import util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;
    private Map<String, Object> myParameterMap;

    // assumes rectangle grid shape for the moment
    public CellGrid(Map<String, Object> parameterMap, String[][] cellGrid) {
        createCellGrid(cellGrid);
        connectGrid(cellGrid);
        myParameterMap = parameterMap;
    }

    private void createCellGrid(String[][] cellGrid) {
        myCellGrid = new Cell[cellGrid.length][cellGrid[0].length];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                myCellGrid[i][j] = new FireCell(State.getState(cellGrid[i][j]), 0.3);
            }
        }
    }

    private void connectGrid(String[][] cellGrid) {
        int[] neighbors = (int[]) myParameterMap.get("neighbors");
        CellShape shape = (CellShape) myParameterMap.get("cell shape");

        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                connectNeighbors(neighbors, shape, i, j);
            }
        }
    }

    private void connectNeighbors(int[] neighbors, CellShape shape, int i, int j) {
        List<Cell> neighborList = new ArrayList<>();

        for (Pair<Integer, Integer> displacement : shape.getDisplacements(neighbors)) {
            int iNeighbor = i + displacement.getKey();
            int jNeighbor = j + displacement.getValue();

            if (inRange(iNeighbor, jNeighbor)) {
                Cell neighbor = myCellGrid[iNeighbor][jNeighbor];
                neighborList.add(neighbor);
            }
        }
        myCellGrid[i][j].setNeighborhood(new Neighborhood(neighborList));
    }

    private boolean inRange(int iNeighbor, int jNeighbor) {
        return inXRange(iNeighbor) && inYRange(jNeighbor);
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