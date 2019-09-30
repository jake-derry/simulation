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

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * The purpose of the Configurer class is to take an XML file as an input and return a simulation
 * as its output. This simulation will be based on the contents of the XML file. The configurer class
 * also checks to make sure that XML parameters are valid. If parameters are not valid, default ones will
 * be used. This class directly depends on ParameterLoader.java and StylingLoader.java for loading in the
 * simulation and styling parameters, respectively. Aside from general Java functionality, this class also
 * depends on DocumentBuilder, DocumentBuilderFactory, Document, and Element.
 *
 * Error checking is implemented, so no compile time errors are expected with normal use of the Configurer class.
 * All public methods within the Configurer class are static. Example of use: getSimulation("Fire.xml") will return
 * a simulation that implements Fire cells.
 *
 * @author Jonah Knapp
 */
public class Configurer {
    //Files Supported
    private static final String SIMULATION_TAG = "Simulation";
    private static final String DEFAULT_TAG = "defaults";
    private static final String STYLE_TAG = "Style";

    //Simulations Supported
    private static final String LIFE = "Game of life";
    private static final String SEGREGATION = "segregation";
    private static final String PREDATOR_PREY = "Predator-Prey";
    private static final String FIRE = "Fire";
    private static final String PERCOLATION = "Percolation";
    private static final String RPS = "RPS";
    private static final String FORAGING = "Foraging";

    //Simulation Values and default parameters
    private static final String STYLE = "StylingFile";
    private static final String SHAPE = "shape";
    private static final String NEIGHBORS = "neighbors";

    private static final int DEFAULT_DELAY = 500;
    private static final String DEFAULT_STYLE = "styles/Style1.xml";
    private static final String DEFAULT_SHAPE = "rectangle";
    private static final int[] DEFAULT_NEIGHBORS = new int[]{0, 1, 2, 3, 4, 5, 6, 7};

    //Simulation-Specific Tags
    private static final String SATISFACTION_PERCENT = "satisfaction";
    private static final String CATCH_PERCENT = "probCatch";
    private static final String BREED_TIME = "breedTime";
    private static final String FOOD_BOOST = "foodBoost";
    private static final String PREDATOR_INITIAL_ENERGY = "initialEnergy";
    private static final String PREDATOR_ENERGY_THRESHOLD = "breedThreshold";
    private static final String RPS_THRESHOLD = "threshold";
    private static final String FORAGING_PHEROMONES = "foragingPheromones";
    private static final String RETURNING_PHEROMONES = "returningPheromones";
    private static final String BIRTH_PROBABILITY = "birthProbability";

    //Default Simulation Files
    private static final String DEFAULT_SIM = "simulations/Fire.xml";
    private static final String DEFAULT_COLORS_FILE = "styles/defaultColors.xml";

    //XML Parsing Errors
    private static final String ERROR_DEFAULT = "XML type \"%s\" not supported. Loading Default File.";
    private static final String SIMULATION_ERROR_DEFAULT = "Simulation type not supported. Loading Default File.";


    /**
     * Reads Simulation XML file. First creates a document using the DocumentBuilder class. Uses this information to
     * create a cellular array and parameterMap, then ultimately passes this information to a new Simulation.
     *
     * @param myFile XML to be read
     * @return Simulation created based on XML file
     */
    public static Simulation getSimulation(String myFile) {
        Element mainElement = readFile(myFile, SIMULATION_TAG);
        ParameterLoader myParams = new ParameterLoader(mainElement);
        int[] dimensionsRC = myParams.getDimensions();
        String defaultState = myParams.getDefaultState();
        String[][] myStateArray = new String[dimensionsRC[0]][dimensionsRC[1]];
        initializeCells(defaultState, myParams, myStateArray);
        HashMap<String, Object> mySpecialValues = new HashMap<>();
        getCommonValues(myParams, mySpecialValues);
        switch (myParams.getSimType()) {
            case PERCOLATION:
            case LIFE:
                break;
            case RPS:
                mySpecialValues.put(RPS_THRESHOLD, myParams.getSpecialValue(RPS_THRESHOLD, 0));
                break;
            case FORAGING:
                mySpecialValues.put(FORAGING_PHEROMONES, myParams.getSpecialValue(FORAGING_PHEROMONES, 0));
                mySpecialValues.put(RETURNING_PHEROMONES, myParams.getSpecialValue(RETURNING_PHEROMONES, 0));
                mySpecialValues.put(BIRTH_PROBABILITY, myParams.getSpecialValue(BIRTH_PROBABILITY, 1));
                break;
            case PREDATOR_PREY:
                mySpecialValues.put(BREED_TIME, myParams.getSpecialValue(BREED_TIME, 0));
                mySpecialValues.put(PREDATOR_INITIAL_ENERGY, myParams.getSpecialValue(PREDATOR_INITIAL_ENERGY, 0));
                mySpecialValues.put(PREDATOR_ENERGY_THRESHOLD, myParams.getSpecialValue(PREDATOR_ENERGY_THRESHOLD, 0));
                mySpecialValues.put(FOOD_BOOST, myParams.getSpecialValue(BREED_TIME, 0));
                break;
            case FIRE:
                mySpecialValues.put(CATCH_PERCENT, myParams.getSpecialValue(CATCH_PERCENT, 1));
                break;
            default:
                new ErrorThrow(SIMULATION_ERROR_DEFAULT);
                return getSimulation(DEFAULT_SIM);
        }
        return new Simulation(mySpecialValues, myStateArray);
    }

    /**
     * Reads Style XML file. First creates a document using the DocumentBuilder class. Returns a map containing the
     * parameters specified within the Styling file.
     *
     * @param myStyleFile File to read Styling from. If file is invalid, a default styling file will used.
     * @return A Map containing Strings as Keys and Values as either a Map, int, or String.
     */
    public static Map<String, Object> getStyling(String myStyleFile){
        Element colorElement = readFile(DEFAULT_COLORS_FILE, DEFAULT_TAG);
        Element mainElement = readFile(myStyleFile, STYLE_TAG);
        StylingLoader myStyling = new StylingLoader(colorElement, mainElement);
        return myStyling.getStyling();
    }


    /**
     * Gets the values for Shape, Neighbors, Style, and Simulation Type, which is common to all
     *
     */
    private static void getCommonValues(ParameterLoader myParams, HashMap<String, Object> mySpecialValues) {
        mySpecialValues.put(SIMULATION_TAG, myParams.getSimType());
        mySpecialValues.put(SHAPE, myParams.getValueString(SHAPE, DEFAULT_SHAPE));
        mySpecialValues.put(NEIGHBORS, myParams.getNeighbors(DEFAULT_NEIGHBORS));
        mySpecialValues.put(STYLE, myParams.getValueString(STYLE_TAG, DEFAULT_STYLE));
    }

    /**
     * Creates a documentBuilder from an XML file then parses it into a document. If the type of document is not
     * a simulation, the simulation will default to a Fire simulation.
     */
    private static Element readFile(String myFile, String FileType) {
        try {
            File simFile = new File("data/" + myFile);
            DocumentBuilder simDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document myDocument = simDocumentBuilder.parse(simFile);
            if (!myDocument.getFirstChild().getNodeName().equals(FileType)) {
                throw new XMLSimulationException(ERROR_DEFAULT, myDocument.getFirstChild().getNodeName());
            } else {
                return myDocument.getDocumentElement();
            }
        } catch (Exception e) {
            new ErrorThrow(e.getMessage());
        }
        if(FileType.equals(SIMULATION_TAG)){
            return readFile(DEFAULT_SIM, FileType);
        }
        else{return readFile(DEFAULT_STYLE, FileType);}
    }

    /**
     * Initializes the cells in the Cellular Array
     */
    private static void initializeCells(String defaultState, ParameterLoader myParams, String[][] myArray) {
        int totalRows = myParams.getDimensions()[0];
        int totalColumns = myParams.getDimensions()[1];
        Map<Integer, String> activeCells = myParams.getActiveCells();
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalColumns; j++) {
                int myIndex = (i * totalColumns) + j;
                myArray[i][j] = activeCells.getOrDefault(myIndex, defaultState);
            }

        }
    }
}
