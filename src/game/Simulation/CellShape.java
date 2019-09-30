package game.Simulation;

import util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CellShape {
    RECTANGLE("rectangle", 4, new int[][] {
            new int[] {0, 1},
            new int[] {1, 1},
            new int[] {-1, 0},
            new int[] {-1, -1},
            new int[] {0, -1},
            new int[] {1, -1},
            new int[] {1, 0},
            new int[] {1, -1},
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

    CellShape(String shapeName, int sides, int[][] displacementMap) {
        myShapeName = shapeName;
        mySides = sides;
        myDisplacementMap = displacementMap;
    }

    /**
     *
     * @param i
     * @return
     */
    public int[] getDisplacement(int i) {
        return myDisplacementMap[i];
    }

    /**
     *
     * @param dexes
     * @return
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
     *
     * @param shapeName
     * @return
     */
    public static CellShape matchShape(String shapeName) {
        for (CellShape shape : CellShape.values()) {
            if (shapeName.equals(shape.getName())) {
                return shape;
            }
        }
        return null;
    }

    private String getName() {
        return myShapeName;
    }

    public int getSides() {
        return mySides;
    }
}