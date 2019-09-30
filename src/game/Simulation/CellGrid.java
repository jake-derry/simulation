package game.Simulation;

import game.Simulation.Cell.Cell;
import game.Simulation.Cell.FireCell;
import game.Simulation.Cell.GameOfLifeCell;
import util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;
    private Map<String, Object> myParameterMap;

    /**
     *
     *
     * @param parameterMap
     * @param cellGrid
     */
    public CellGrid(Map<String, Object> parameterMap, String[][] cellGrid) {
        myParameterMap = parameterMap;
        createCellGrid(cellGrid);
        connectGrid(cellGrid);
    }

    /**
     *
     * @param cellGrid
     */
    private void createCellGrid(String[][] cellGrid) {
        myCellGrid = new Cell[cellGrid.length][cellGrid[0].length];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                myCellGrid[i][j] = new GameOfLifeCell(State.getState(cellGrid[i][j]));
            }
        }
    }

    /**
     *
     * @param cellGrid
     */
    private void connectGrid(String[][] cellGrid) {
        int[] neighbors = (int[]) myParameterMap.get("neighbors");
        CellShape shape = CellShape.matchShape((String) myParameterMap.get("shape"));

        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                connectNeighbors(neighbors, shape, i, j);
            }
        }
    }

    /**
     *
     * @param neighbors
     * @param shape
     * @param i
     * @param j
     */
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

    /**
     *
     * @param iNeighbor
     * @param jNeighbor
     * @return
     */
    private boolean inRange(int iNeighbor, int jNeighbor) {
        return inXRange(iNeighbor) && inYRange(jNeighbor);
    }

    /**
     *
     * @param jNeighbor
     * @return
     */
    private boolean inYRange(int jNeighbor) {
        return jNeighbor >= 0 && jNeighbor < myCellGrid.length;
    }

    /**
     *
     * @param iNeighbor
     * @return
     */
    private boolean inXRange(int iNeighbor) {
        return iNeighbor >= 0 && iNeighbor < myCellGrid[0].length;
    }

    @Override
    public Iterator<Cell> iterator() {
        return new CellIterator(myCellGrid);
    }

    /**
     *
     * @return
     */
    public int getCellRows(){
        return myCellGrid.length;
    }

    /**
     *
     * @return
     */
    public int getCellColumns(){
        return myCellGrid[0].length;
    }

    /**
     *
     * @return
     */
    public CellShape getShape() {
        return CellShape.matchShape((String) myParameterMap.get("shape"));
    }
}