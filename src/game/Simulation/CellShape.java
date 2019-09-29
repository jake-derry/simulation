package game.Simulation;

import util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CellShape {
    RECTANGLE("rectangle", new int[][] {
            new int[] {0, 1},
            new int[] {1, 1},
            new int[] {-1, 0},
            new int[] {-1, -1},
            new int[] {0, -1},
            new int[] {1, -1},
            new int[] {1, 0},
            new int[] {1, -1},
    }),
    TRIANGLE("triangle", new int[][] {
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
    HEXAGON("hexagon", new int[][] {
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

    CellShape(String shapeName, int[][] displacementMap) {
        myShapeName = shapeName;
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

    public List<Pair<Integer, Integer>> getDisplacements(int[] dexes) {
        List<Pair<Integer, Integer>> selectDisplacements = new ArrayList<>();
        for (int dex : dexes) {
            selectDisplacements.add(
                    new Pair<Integer, Integer>(getDisplacement(dex)[0],
                                               getDisplacement(dex)[1]));
        }
        return selectDisplacements;
    }

}