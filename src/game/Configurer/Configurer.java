package game.Configurer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import game.Simulation.Cell.Cell;
import game.Simulation.*;
import org.w3c.dom.Document;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * The purpose of the Configurer class is to take an XML file as an input and return a simulation
 * as its output. This simulation will be based on the contents of the XML file.
 */
public final class Configurer{
    //Tags used within XML file
    private static final String COLUMN_TAG = "columns";
    private static final String ROW_TAG = "rows";
    private static final String STATE_TAG = "defaultState";
    private static final String CELL_COLUMN_TAG = "col";
    private static final String CELL_ROW_TAG = "row";
    private static final String CELL_STATE_TAG = "state";
    private static final String TYPE_TAG = "type";

    //Simulations Supported
    private static final String LIFE = "gameOfLife";
    private static final String SEGREGATION = "segregation";
    private static final String PREDATOR_PREY = "predatorPrey";
    private static final String FIRE = "fire";
    private static final String PERCOLATION = "percolation";

    //Simulation-Specific Tags
    private static final String SATISFACTION_PERCENT = "satisfaction";
    private static final String CATCH_PERCENT = "probCatch";
    private static final String BREED_TIME = "breedTime";
    private static final String PREDATOR_INITIAL_ENERGY = "initEnergy";
    private static final String PREDATOR_ENERGY_THRESHOLD = "energyThreshold";

    /**Reads XML file. First creates a document using the DocumentBuilder class. Uses this information to create a
     * cellular array and ultimately passes this information, along with WindowSize, to a new Simulation.
     *
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation getSimulation(String myFile, int WindowSize, String language){
        Document simDoc = readFile(myFile);
        Element mainElement = simDoc.getDocumentElement();
        int totalColumns = getFirstElementInteger(mainElement,  COLUMN_TAG);
        int totalRows = getFirstElementInteger(mainElement,  ROW_TAG);
        int defaultState = getFirstElementInteger(mainElement,  STATE_TAG);
        Cell[][] myCellArray = new Cell[totalRows][totalColumns];
        List<Integer> activeCells = initializeActiveCells(simDoc, myCellArray);
        initializeDefaultCells(totalRows, totalColumns, defaultState, activeCells, myCellArray);
        switch (getFirstElementString(mainElement, TYPE_TAG)){
            case LIFE:
                return new GameOfLifeSimulation(LIFE, myCellArray);
            case SEGREGATION:
                double satisfaction = getFirstElementInteger(mainElement,  SATISFACTION_PERCENT);
                return new SegregationSimulation(SEGREGATION, myCellArray, satisfaction/100);
            case PREDATOR_PREY:
                int breedTime = getFirstElementInteger(mainElement, BREED_TIME);
                int initialEnergy = getFirstElementInteger(mainElement, PREDATOR_INITIAL_ENERGY);
                int energyThreshold = getFirstElementInteger(mainElement, PREDATOR_ENERGY_THRESHOLD);
                return new PredatorPreySimulation(PREDATOR_PREY, myCellArray, breedTime,
                        initialEnergy, energyThreshold);
            case FIRE:
                double chance = getFirstElementInteger(mainElement,  CATCH_PERCENT);
                return new FireSimulation(FIRE, myCellArray, chance/100);
            case PERCOLATION:
                return new PercolationSimulation(PERCOLATION, myCellArray);

        }
        return null;
    }

    /**
     * Creates a documentBuilder from an XML file then parses it into a document
     */
    private static Document readFile(String myFile){
        try{
            File simFile = new File("data/" + myFile);
            DocumentBuilder simDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return simDocumentBuilder.parse(simFile);
        } catch (Exception e) {
            errorAlert(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param myDoc Document to be read
     * @param myArray Array to save initialized cells in
     *
     * @return List of integers containing the indexes of cells that have been initialized
     */
    private static List<Integer> initializeActiveCells(Document myDoc, Cell[][] myArray){
        NodeList nList = myDoc.getElementsByTagName("cell");
        List<Integer> activeCells = new ArrayList<>();
        int totalCols = myArray.length;
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myCell = (Element) nList.item(i);
            int myRow = getFirstElementInteger(myCell, CELL_ROW_TAG);
            int myColumn = getFirstElementInteger(myCell, CELL_COLUMN_TAG);
            int myState = getFirstElementInteger(myCell, CELL_STATE_TAG);
            myArray[myRow-1][myColumn-1] = new Cell(myState);
            activeCells.add((myRow-1) * totalCols + myColumn-1);
        }
        return activeCells;
    }

    /**
     * Initializes default cells in the Cellular Array
     */
    private static void initializeDefaultCells(int Rows, int Cols, int state, List<Integer> activeCells, Cell[][] myArray){
        for(int i = 0; i < Rows; i++){
            for(int j = 0; j<Cols; j++){
                if(!(activeCells.contains((i*Cols) + j))){
                    myArray[i][j] = new Cell(state);
                }
            }
        }
    }

    /**
     * Prints cells that have been initialized, as specified by the XML application
     */
    private static void printActiveCells(Document myDoc) {
        NodeList nList = myDoc.getElementsByTagName("cell");
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myCell = (Element) nList.item(i);
            System.out.println("Cell " + i + ":");
            System.out.println(CELL_ROW_TAG + " " + getFirstElementString(myCell, CELL_ROW_TAG));
            System.out.println(CELL_COLUMN_TAG + " " + getFirstElementString(myCell, CELL_COLUMN_TAG));
            System.out.println(CELL_STATE_TAG + " " + getFirstElementString(myCell, CELL_STATE_TAG));
        }
    }


    /** Returns a String containing the name of the first sub-element of an element
     */
    private static String getFirstElementString(Element myElement, String TagName){
        return myElement.getElementsByTagName(TagName).item(0).getTextContent();
    }

    /** Returns a Integer representation of the first sub-element of an element
     */
    private static Integer getFirstElementInteger(Element myElement, String TagName){
        return Integer.parseInt(myElement.getElementsByTagName(TagName).item(0).getTextContent());
    }


    /**Displays an alert on the screen that requires handling by the user
     */
    private static void errorAlert(String myErrorMessage){
        new Alert(AlertType.ERROR, myErrorMessage).showAndWait();
    }
}
