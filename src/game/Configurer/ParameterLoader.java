package game.Configurer;

import game.Configurer.ExceptionHandlers.ErrorThrow;
import game.Configurer.ExceptionHandlers.XMLSimulationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class handles loading in parameters from the XML file. It contains default values for each of the parameters,
 * which is set when the specified values in the XML are not supported.
 *
 * @author Jonah Knapp
 */
public class ParameterLoader {
    //Tags used within XML file
    private static final String COLUMN_TAG = "columns";
    private static final String ROW_TAG = "rows";
    private static final String MAX_STATE_TAG = "highestState";
    private static final String STATE_TAG = "defaultState";
    private static final String CELL_COLUMN_TAG = "col";
    private static final String CELL_ROW_TAG = "row";
    private static final String CELL_STATE_TAG = "state";
    private static final String TYPE_TAG = "type";
    private static final String DISTRIBUTION_TAG = "cellDistribution";

    //Types of distributions supported
    private static final String SPECIFIC_DISTRIBUTION = "cell";
    private static final String RANDOM_DISTRIBUTION = "random";
    private static final String CONCENTRATION_DISTRIBUTION = "concentration";
    private static final String DISTRIBUTION_VALUE_TAG = "value";

    private static final int DEFAULT_INT = -1;
    private static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_GENERIC = 5;
    private static final int DEFAULT_PERCENTAGE = 50;

    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;
    private int totalRows;
    private int totalColumns;
    private String simType;
    private int highestState;
    private String distributionType;

    ParameterLoader(Element mainDocumentElement){
        mainElement = mainDocumentElement;
        totalRows = getFirstElementInteger(mainElement, ROW_TAG);
        totalColumns = getFirstElementInteger(mainElement, COLUMN_TAG);
        simType = getFirstElementString(TYPE_TAG);
        highestState = getFirstElementInteger(mainElement, MAX_STATE_TAG);
        if(highestState == -1){ highestState = 1;}
        distributionType = getFirstElementString(DISTRIBUTION_TAG);
    }

    /**
     * Checks the validity of dimensions supplied by the XML file. If the dimensions are not supported,
     * then the method will return corrected values. Note: If any one of the dimension values is incorrect,
     * the default value will apply to BOTH dimensions.
     *
     * @return valid dimensions of the cell array, organized as [Rows, Columns]
     */
    public int[] getDimensions(){
        if(totalRows < 0 || totalColumns < 0){
            totalRows = DEFAULT_DIMENSION;
            totalColumns = DEFAULT_DIMENSION;
        }
        return new int[]{totalRows, totalColumns};
    }

    /**
     *
     * @return default state, as defined in the XML file
     */
    public int getDefaultState(){
        return getFirstElementInteger(mainElement, STATE_TAG);
    }

    /**
     *
     * @return type of simulation, as described in the XML file
     */
    public String getSimType(){
        return simType;
    }

    /**
     *
     * @param parameterIdentifier Identifier of specific parameter
     * @param Type equal to 1 if the parameter is a percentage
     * @return Value of the parameter, as specified in the XML file. If the value is invalid (not an integer,
     * greater than 100
     */
    public int getSpecialValue(String parameterIdentifier, int Type){
        int myParameter = getFirstElementInteger(mainElement, parameterIdentifier);
        if(Type == 1 && (myParameter < 0 || myParameter > 100)){myParameter = DEFAULT_PERCENTAGE;}
        if(myParameter < 0 ){ myParameter = DEFAULT_GENERIC; }
        return myParameter;
    }

    /**
     * Finds active cells based on the distribution type, which is specified within the XML file. If no distribution
     * is specified, it is defaulted to the specific distribution type.
     *
     * @return a Map containing active cells as Keys and their corresponding states as Values.
     */
    public Map<Integer, Integer> getActiveCells(){
        HashMap <Integer, Integer> activeCells = new HashMap<>();
        NodeList nList = mainElement.getElementsByTagName(distributionType);
        List<Integer> openCells = IntStream.rangeClosed(0, totalRows*totalColumns-1)
                .boxed().collect(Collectors.toList());
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myDistribution = (Element) nList.item(i);
            int myState = getFirstElementInteger(myDistribution, CELL_STATE_TAG);
            switch (distributionType){
                case RANDOM_DISTRIBUTION:
                    getRandomCells(activeCells, openCells, myState,
                            getFirstElementInteger(myDistribution, DISTRIBUTION_VALUE_TAG));
                    break;
                case CONCENTRATION_DISTRIBUTION:
                    int totalCells = totalRows * totalColumns *
                            getFirstElementInteger(myDistribution, DISTRIBUTION_VALUE_TAG) / 100;
                    getRandomCells(activeCells, openCells, myState, totalCells);
                    break;
                default:
                    getSpecificCells(myDistribution, activeCells);
                    break;
            }

        }
        return activeCells;
    }

    /**
     * Method used to read the active cells within the XML file
     */
    private void getSpecificCells(Element myCell, Map<Integer, Integer> activeCells) {
        int myRow = getFirstElementInteger(myCell, CELL_ROW_TAG);
        int myColumn = getFirstElementInteger(myCell, CELL_COLUMN_TAG);
        int myState = getFirstElementInteger(myCell, CELL_STATE_TAG);
        if(myRow != -1 && myColumn != -1 && myState <= highestState){
            activeCells.put((myRow-1) * totalColumns + myColumn-1, myState);
        }

    }

    /**
     * Method used to generate a random distribution of cells, based on either the concentration of
     * each state or the type of
     */
    private void getRandomCells(Map<Integer, Integer> activeCells, List<Integer> openCells, int state, int totalCells) {
        int total = 0;
        System.out.println(totalCells);
        if(openCells.size() > 0 && state >= 0 && state <= highestState && totalCells > 0){
            for(int i = 0; i < totalCells; i++){
                total++;
                int randomCell = (int)(Math.random() * openCells.size());
                int myIndex = openCells.remove(randomCell);
                System.out.println(total + ":" + myIndex + " "+  state);
                activeCells.put(myIndex, state);
            }
        }
    }
    /**
     * Returns a Integer representation of the first sub-element of an element. If the specified element
     * does not contain an integer, then a default integer value is used.
     */
    private Integer getFirstElementInteger(Element myElement, String TagName){
        Node myValue = myElement.getElementsByTagName(TagName).item(0);
        if(myValue == null){
           new ErrorThrow(NOT_FOUND, TagName);
           return DEFAULT_INT;
        }
        try{
            int x = Integer.parseInt(myValue.getTextContent());
            return x;
        } catch (Exception e){
            try{
                int x = Integer.parseInt(myValue.getTextContent().replaceAll("\\s+", ""));
                return x;
            } catch (Exception e2){
                new ErrorThrow(e2.getMessage());
            }
        }
        return DEFAULT_INT;
    }

    /**
     * Returns a String containing the name of the first sub-element of an element
     */
    private String getFirstElementString(String TagName){
        Node myValue = mainElement.getElementsByTagName(TagName).item(0);
        if(myValue == null){
            new ErrorThrow(NOT_FOUND, TagName);
            return "Unspecified";
        }
        return mainElement.getElementsByTagName(TagName).item(0).getTextContent();
    }

}
