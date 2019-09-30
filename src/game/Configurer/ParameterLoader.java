package game.Configurer;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static game.Configurer.XMLReaders.getFirstElementString;
import static game.Configurer.XMLReaders.getFirstElementInteger;

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
    private static final String NEIGHBORS_TAG = "neighbor";
    private static final String INDEX_TAG = "index";

    //Types of distributions supported
    private static final String SPECIFIC_DISTRIBUTION = "cell";
    private static final String RANDOM_DISTRIBUTION = "random";
    private static final String CONCENTRATION_DISTRIBUTION = "concentration";
    private static final String DISTRIBUTION_VALUE_TAG = "value";

    private static final int DEFAULT_DIMENSION = 10;
    private static final int DEFAULT_GENERIC = 5;
    private static final int DEFAULT_PERCENTAGE = 50;

    //Variables that all simulations have
    private Element mainElement;
    private int totalRows;
    private int totalColumns;
    private String simType;
    private String distributionType;

    public ParameterLoader(Element mainDocumentElement){
        mainElement = mainDocumentElement;
        totalRows = getFirstElementInteger(mainElement, ROW_TAG);
        totalColumns = getFirstElementInteger(mainElement, COLUMN_TAG);
        distributionType = getFirstElementString(mainElement, DISTRIBUTION_TAG);
        simType = getFirstElementString(mainElement, TYPE_TAG);
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
    public String getDefaultState(){
        return getFirstElementString(mainElement, STATE_TAG);
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
    public Map<Integer, String> getActiveCells(){
        HashMap <Integer, String> activeCells = new HashMap<>();
        NodeList nList = mainElement.getElementsByTagName(distributionType);
        List<Integer> openCells = IntStream.rangeClosed(0, totalRows*totalColumns-1)
                .boxed().collect(Collectors.toList());
        for(int i = 0 ; i < nList.getLength(); i++){
            Element myDistribution = (Element) nList.item(i);
            String myState = getFirstElementString(myDistribution, CELL_STATE_TAG);
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

    public int getValueInt(String myTag, int DefaultValue){
        int myVal = getFirstElementInteger(mainElement, myTag);
        if(myVal < 0){
            return DefaultValue;
        }
        return myVal;
    }

    public String getValueString(String myTag, String DefaultValue){
        String myVal = getFirstElementString(mainElement, myTag);
        if(myVal.equals("Unspecified")){
            return DefaultValue;
        }
        return myVal;
    }

    public String getSimType() {
        return simType;
    }

    public int[] getNeighbors(int[] Default) {
        NodeList nList = mainElement.getElementsByTagName(NEIGHBORS_TAG);
        List<Integer> myNeighbors = new ArrayList<>();
        if (nList.getLength() < 0){
            return Default;
        }
        for(int i = 0 ; i < nList.getLength(); i++){
            Element neighbor = (Element) nList.item(i);
            int myIndex = getFirstElementInteger(neighbor, INDEX_TAG);
            if(myIndex > 0){ myNeighbors.add(myIndex);}
        }
        return myNeighbors.stream().mapToInt(i -> i).toArray();
    }
    /**
     * Method used to read the active cells within the XML file
     */
    private void getSpecificCells(Element myCell, Map<Integer, String> activeCells) {
        int myRow = getFirstElementInteger(myCell, CELL_ROW_TAG);
        int myColumn = getFirstElementInteger(myCell, CELL_COLUMN_TAG);
        String myState = getFirstElementString(myCell, CELL_STATE_TAG);
        if(myRow != -1 && myColumn != -1 ){
            activeCells.put((myRow-1) * totalColumns + myColumn-1, myState);
        }

    }

    /**
     * Method used to generate a random distribution of cells, based on either the concentration of
     * each state or the type of
     */
    private void getRandomCells(Map<Integer, String> activeCells, List<Integer> openCells, String state, int totalCells) {
        if(openCells.size() > 0 && totalCells > 0){
            for(int i = 0; i < totalCells; i++){
                int randomCell = (int)(Math.random() * openCells.size());
                int myIndex = openCells.remove(randomCell);
                activeCells.put(myIndex, state);
            }
        }
    }
}
