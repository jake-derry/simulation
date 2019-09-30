package game.Simulation;

import game.Simulation.Cell.*;
import util.Pair;

import java.util.*;

/**
 * An Iterable class that protects the array of cells.
 */
public class CellGrid implements Iterable<Cell> {
    private Cell[][] myCellGrid;
    private Map<String, Object> myParameterMap;

    /**
     * Constructor for CellGrid that initializes the cell grid
     * based on a map of parameters and a grid of strings that
     * correspond to states.
     *
     * @param parameterMap          Map of parameters
     * @param cellGrid              A grid of strings that correspond to
     *                              states
     */
    public CellGrid(Map<String, Object> parameterMap, String[][] cellGrid) {
        myParameterMap = parameterMap;
        createCellGrid(cellGrid);
        connectGrid(cellGrid);
    }

    /**
     * Creates the cell grid for each simulation type
     *
     * @param cellGrid              String array
     */
    private void createCellGrid(String[][] cellGrid) {
        myCellGrid = new Cell[cellGrid.length][cellGrid[0].length];
        for (int i = 0; i < cellGrid.length; i++) {
            for (int j = 0; j < cellGrid[0].length; j++) {
                switch ((String) myParameterMap.get("Simulation")) {
                    case ("Fire") : {
                        myCellGrid[i][j] = new FireCell(State.getState(cellGrid[i][j]), (double) myParameterMap.get("probCatch"));
                    }
                    case ("Game of life") : {
                        myCellGrid[i][j] = new GameOfLifeCell(State.getState(cellGrid[i][j]));
                    }
                    case ("Percolation") : {
                        myCellGrid[i][j] = new PercolationCell(State.getState(cellGrid[i][j]));
                    }
                    case ("Predator-Prey") : {
                        myCellGrid[i][j] = new PredatorPreyCell(State.getState(cellGrid[i][j]),
                                (int) myParameterMap.get("initialEnergy"), (int) myParameterMap.get("foodBoost"),
                                (int) myParameterMap.get("breedThreshold"), (int) myParameterMap.get("breedTime"));
                    }
                    case ("RPS") : {
                        myCellGrid[i][j] = new RPSCell(State.getState(cellGrid[i][j]),
                                (int) myParameterMap.get("threshold"));
                    }
                    case ("Foraging") : {
                        myCellGrid[i][j] = new ForagingCell(State.getState(cellGrid[i][j]),
                                (int) myParameterMap.get("foragingPheromones"),
                                (int) myParameterMap.get("returningPheromones"),
                                (double) myParameterMap.get("birthProbability"));
                    }
                }
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
        Map<Pair<Integer, Integer>, Cell> neighborMap = new TreeMap<>();

        for (Pair<Integer, Integer> displacement : shape.getDisplacements(neighbors)) {
            int iNeighbor = i + displacement.getKey();
            int jNeighbor = j + displacement.getValue();

            if (inRange(iNeighbor, jNeighbor)) {
                Cell neighbor = myCellGrid[iNeighbor][jNeighbor];
                neighborMap.put(displacement, neighbor);
            }
        }
        myCellGrid[i][j].setNeighborhood(new Neighborhood(neighborMap));
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