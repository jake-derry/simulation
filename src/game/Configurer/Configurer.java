package game.Configurer;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import game.Configurer.ExceptionHandlers.ErrorThrow;
import game.Configurer.ExceptionHandlers.XMLSimulationException;
import game.Simulation.Cell.Cell;
import game.Simulation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;



/**
 * The purpose of the Configurer class is to take an XML file as an input and return a simulation
 * as its output. This simulation will be based on the contents of the XML file. The configurer class
 * also checks to make sure that XML parameters are valid. If parameters are not valid, default ones will
 * be used.
 *
 * @author Jonah Knapp
 */
public class Configurer {
    //Files Supported
    private static final String SIMULATION_TAG = "Simulation";
    private static final String STYLE_TAG = "Style";
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

    //Default Simulation File
    private static final String DEFAULT_SIM = "././Fire.xml";

    //XML Parsing Errors
    private static final String ERROR_DEFAULT = "XML type \"%s\" not supported. Loading Default File.";
    private static final String SIMULATION_ERROR_DEFAULT = "Simulation type not supported. Loading Default File.";


    /**
     * Reads XML file. First creates a document using the DocumentBuilder class. Uses this information to create a
     * cellular array and ultimately passes this information, along with WindowSize, to a new Simulation.
     *
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation getSimulation(String myFile) {
        Document simDoc = readFile(myFile, SIMULATION_TAG);
        Element mainElement = simDoc.getDocumentElement();
        ParameterLoader myParams = new ParameterLoader(mainElement);
        int[] dimensionsRC = myParams.getDimensions();
        int defaultState = myParams.getDefaultState();
        Cell[][] myCellArray = new Cell[dimensionsRC[0]][dimensionsRC[1]];
        initializeCells(defaultState, myParams, myCellArray);
        switch (myParams.getSimType()) {
            case LIFE:
                return new GameOfLifeSimulation(LIFE, myCellArray);
            case SEGREGATION:
                double satisfaction = myParams.getSpecialValue(SATISFACTION_PERCENT, 1);
                return new SegregationSimulation(SEGREGATION, myCellArray, satisfaction / 100);
            case PREDATOR_PREY:
                int breedTime = myParams.getSpecialValue(BREED_TIME, 0);
                int initialEnergy = myParams.getSpecialValue(PREDATOR_INITIAL_ENERGY, 0);
                int energyThreshold = myParams.getSpecialValue(PREDATOR_ENERGY_THRESHOLD, 0);
                return new PredatorPreySimulation(PREDATOR_PREY, myCellArray, breedTime,
                        initialEnergy, energyThreshold);
            case FIRE:
                double chance = myParams.getSpecialValue(CATCH_PERCENT, 1);
                return new FireSimulation(FIRE, myCellArray, chance / 100);
            case PERCOLATION:
                return new PercolationSimulation(PERCOLATION, myCellArray);

        }
        new ErrorThrow(SIMULATION_ERROR_DEFAULT);
        return getSimulation(DEFAULT_SIM);
    }

    public static Map<String, String> getStyling(String myStyleFile){
        Document styleDoc = readFile(myStyleFile, );
        Element mainElement = styleDoc.getDocumentElement();
        StylingLoader myStyling = new StylingLoader(mainElement);
        return myStyling.getStyling();
    }

    /**
     * Saving XML file:
     */
    public static void saveStates(String myFile, Cell[][] CellArray){

    }

    /**
     * Creates a documentBuilder from an XML file then parses it into a document. If the type of document is not
     * a simulation, the simulation will default to a Fire simulation.
     */
    private static Document readFile(String myFile, String FileType) {
        try {
            File simFile = new File("data/" + myFile);
            DocumentBuilder simDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document myDocument = simDocumentBuilder.parse(simFile);
            if (!myDocument.getFirstChild().getNodeName().equals(FileType)) {
                throw new XMLSimulationException(ERROR_DEFAULT, myDocument.getFirstChild().getNodeName());
            } else {
                return myDocument;
            }
        } catch (Exception e) {
            new ErrorThrow(e.getMessage());
        }
        return readFile(DEFAULT_SIM);
    }

    /**
     * Initializes the cells in the Cellular Array
     */
    private static void initializeCells(int defaultState, ParameterLoader myParams, Cell[][] myArray) {
        int totalRows = myParams.getDimensions()[0];
        int totalColumns = myParams.getDimensions()[1];
        Map<Integer, Integer> activeCells = myParams.getActiveCells();
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                int myIndex = (i * totalColumns) + j;
                if (activeCells.containsKey(myIndex)) {
                    myArray[i][j] = new Cell(activeCells.get(myIndex));
                } else {
                    myArray[i][j] = new Cell(defaultState);
                }
            }

        }
    }
}
