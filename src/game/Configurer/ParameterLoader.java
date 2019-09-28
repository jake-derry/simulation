package game.Configurer;

import game.Configurer.ExceptionHandlers.ErrorThrow;
import game.Configurer.ExceptionHandlers.XMLSimulationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

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

    private static final int DEFAULT_INT = -1;
    private static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_GENERIC = 5;
    private static final int DEFAULT_PERCENTAGE = 50;

    private static final String BAD_DIMENSIONS = "Unsupported dimensions specified. Using default value of %s x %s.";
    private static final String NOT_FOUND = "Parameter type %s not found. Default value will be used.";

    private Element mainElement;
    private int totalRows;
    private int totalColumns;
    private String simType;
    private int highestState;

    ParameterLoader(Element mainDocumentElement){
        mainElement = mainDocumentElement;
        totalRows = getFirstElementInteger(mainElement, ROW_TAG);
        totalColumns = getFirstElementInteger(mainElement, COLUMN_TAG);
        simType = getFirstElementString(TYPE_TAG);
        highestState = getFirstElementInteger(mainElement, MAX_STATE_TAG);
        if(highestState == -1){ highestState = 1;}
    }

    /**Checks the validity of dimensions supplied by the XML file. If the dimensions are not supported,
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
     *
     * @return a Map containing active cells as Keys and their corresponding states as Values. If the specified state is
     * not valid.
     */
    public Map<Integer, Integer> getActiveCells(){
        NodeList nList = mainElement.getElementsByTagName("cell");
        HashMap <Integer, Integer> activeCells = new HashMap<>();
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myCell = (Element) nList.item(i);
            int myRow = getFirstElementInteger(myCell, CELL_ROW_TAG);
            int myColumn = getFirstElementInteger(myCell, CELL_COLUMN_TAG);
            int myState = getFirstElementInteger(myCell, CELL_STATE_TAG);
            if(myRow != -1 && myColumn != -1 && myState <= highestState){
                activeCells.put((myRow-1) * totalColumns + myColumn-1, myState);
            }
        }
        return activeCells;
    }

    /** Returns a Integer representation of the first sub-element of an element. If the specified element
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

    /** Returns a String containing the name of the first sub-element of an element
     */
    private String getFirstElementString(String TagName){
        return mainElement.getElementsByTagName(TagName).item(0).getTextContent();
    }

}
