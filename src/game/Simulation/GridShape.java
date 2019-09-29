package game.Simulation;

import java.util.AbstractMap;
import java.util.List;

public enum GridShape {
    RECTANGLE("rectangle.xml"),
    TRIANGLE("triangle.xml"),
    HEXAGON("hexagon.xml"),
    ;

    private List<AbstractMap.SimpleEntry<Integer, Integer>> myDisplacementMap;
    private String myShape;

    GridShape(String filename) {

    }
}