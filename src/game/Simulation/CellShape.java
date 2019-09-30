package game.Simulation;

import util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerates the shapes of cells that are options making neighbor assignment
 * flexible.
 */
public enum CellShape {
    RECTANGLE("rectangle", 4, new int[][] {
            new int[] {0, 1},
            new int[] {1, 1},
            new int[] {-1, 0},
            new int[] {-1, -1},
            new int[] {0, -1},
            new int[] {1, -1},
            new int[] {1, 0},
            new int[] {-1, 1},
    }),
    TRIANGLE("triangle", 3, new int[][] {
            new int[] {1, 0},
            new int[] {1, 1},
            new int[] {0, 1},
            new int[] {0, -2},
            new int[] {-1, -2},
            new int[] {-1, 1},
            new int[] {-1, 0},
            new int[] {-1, -1},
            new int[] {-1, -2},
            new int[] {0, -2},
            new int[] {0, -1},
            new int[] {1, -1},
    }),
    HEXAGON("hexagon", 6, new int[][] {
            new  int[] {1, 1},
            new int[] {0, 1},
            new int[] {-1, 1},
            new int[] {-1, 0},
            new int[] {0, -1},
            new int[] {1, 0},
    }),
    ;

    private String myShapeName;
    private int[][] myDisplacementMap;
    private int mySides;

    /**
     * Initializes cell shape based on a name, number of sides, and
     * a map of displacements to indices which is stored as an array
     * @param shapeName         String name of the shape
     * @param sides             Number of sides
     * @param displacementMap   Array of displacements
     */
    CellShape(String shapeName, int sides, int[][] displacementMap) {
        myShapeName = shapeName;
        mySides = sides;
        myDisplacementMap = displacementMap;
    }

    /**
     * Returns a specific displacement given an index
     *
     * @param i         Index
     * @return          A displacement coordinate
     */
    public int[] getDisplacement(int i) {
        return myDisplacementMap[i];
    }

    /**
     * Returns a list of displacement pairs given
     * an array of indices
     *
     * @param dexes     An array of indices
     * @return          A list of displacement pairs
     */
    public List<Pair<Integer, Integer>> getDisplacements(int[] dexes) {
        List<Pair<Integer, Integer>> selectDisplacements = new ArrayList<>();
        for (int dex : dexes) {
            selectDisplacements.add(
                    new Pair<Integer, Integer>(getDisplacement(dex)[0],
                                               getDisplacement(dex)[1]));
        }
        return selectDisplacements;
    }

    /**
     * Matches a string to a cell shape object
     *
     * @param shapeName     String name
     * @return              Cell shape object
     */
    public static CellShape matchShape(String shapeName) {
        for (CellShape shape : CellShape.values()) {
            if (shapeName.equals(shape.getName())) {
                return shape;
            }
        }
        return null;
    }

    /**
     * Gets name
     *
     * @return      Name
     */
    private String getName() {
        return myShapeName;
    }

    /**
     * Gets sides
     *
     * @return      Number of sides
     */
    public int getSides() {
        return mySides;
    }
}