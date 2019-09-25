package game.Simulation;

import java.util.AbstractMap;
import java.util.List;

public enum GridShape {
    RECTANGLE("rectangle.xml", "Rectangle"),
    TRIANGLE("triangle.xml", "Triangle"),
    HEXAGON("hexagon.xml", "Hexagon"),
    ;

    private List<AbstractMap.SimpleEntry<Integer, Integer>> myDisplacementMap;
    private String myShape;

    GridShape(String filename, String shape) { }
}